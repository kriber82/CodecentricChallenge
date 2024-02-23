package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ForGettingDeveloperProfiles;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class GetCodecentricDeveloperProfilesFromGithub implements ForGettingDeveloperProfiles {

    private final static Logger logger = LoggerFactory.getLogger(GetCodecentricDeveloperProfilesFromGithub.class);

    private final String githubBearerToken;
    private final int maxChildRequests;

    public GetCodecentricDeveloperProfilesFromGithub() {
        this(Integer.MAX_VALUE);
    }

    public GetCodecentricDeveloperProfilesFromGithub(int maxChildRequests) {
        this.maxChildRequests = maxChildRequests;
        this.githubBearerToken = System.getenv("GITHUB_API_TOKEN");
        if (this.githubBearerToken == null)
            logger.warn("GITHUB_API_TOKEN not found. Github API requests will be rate-limited.\nPlease store a Github API token in the environment variable GITHUB_API_TOKEN.");
    }

    @Override
    public List<Developer> getDevelopers() {

        GithubProfileJson[] profiles = getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        return Arrays.stream(profiles).
                limit(maxChildRequests).
                parallel().
                map(p -> toDeveloper(p, maxChildRequests)).
                toList();
    }

    private Developer toDeveloper(GithubProfileJson p, int maxChildRequests) {
        var repos = Arrays.stream(getRepositories(p.repos_url)).
                limit(maxChildRequests).
                parallel().
                map(this::toRepo).
                toList();
        return new Developer(p.login, repos); //TODO use display name instead of login
    }

    private SourceCodeRepository toRepo(GithubRepositoryJson r) {
        var languages = getLanguages(r.languages_url);
        return new SourceCodeRepository(r.name, languages);
    }

    public GithubProfileJson[] getOrganizationMembers(String organizationMembersUrl) {
        return getAllPages(organizationMembersUrl, GithubProfileJson.class);
    }

    public GithubRepositoryJson[] getRepositories(String profileReposUrl) {
        return getAllPages(profileReposUrl, GithubRepositoryJson.class);
    }

    public List<ProgrammingLanguage> getLanguages(String repoLanguagesUrl) {
        Map<String, Integer> response = getSingleObject(repoLanguagesUrl, new ParameterizedTypeReference<Map<String, Integer>>() {});

        return response.keySet().stream().map(ProgrammingLanguage::new).toList();
    }

    private <T> T getSingleObject(String url, ParameterizedTypeReference<T> objectType) {
        RestClient restClient = buildAuthorizingClient();

        T response = restClient.get()
                .uri(url)
                .retrieve()
                .body(objectType);
        return response;
    }

    private RestClient buildAuthorizingClient() {
        var builder = RestClient.builder();
        if (githubBearerToken != null)
            builder.defaultHeader("Authorization", "Bearer " + githubBearerToken);
        return builder.build();
    }

    private <T> PaginatedResult<T> getSinglePage(URI url, Class<T> type) {
        RestClient restClient = buildAuthorizingClient();

        var response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(type.arrayType());
        var paginationLinks = parsePaginationHeader(response.getHeaders().getFirst("link"));
        var body = response.getBody();
        return new PaginatedResult<>((T[])body, paginationLinks);
    }

    private <T> T[] getAllPages(String url, Class<T> type) {
        Stream<T> resultStream = Stream.empty();
        var nextUrl = URI.create(url);
        while (nextUrl != null) {
            var currentPage = getSinglePage(nextUrl, type);
            resultStream = Stream.concat(resultStream, Arrays.stream(currentPage.payload()));
            nextUrl = currentPage.paginationLinks().next();
        }
        return resultStream.toArray(size -> (T[]) Array.newInstance(type.arrayType().componentType(), size));
    }

    private static final Pattern paginationHeaderEntryPattern = Pattern.compile("<(?<uri>[^>]*)>; rel=\"(?<rel>[^\"]*)\"");
    public PaginationLinks parsePaginationHeader(String linkHeader) {
        URI first = null;
        URI previous = null;
        URI next = null;
        URI last = null;
        if (linkHeader != null) {
            var paginationHeaderEntryMatcher = paginationHeaderEntryPattern.matcher(linkHeader);
            boolean matchFound = paginationHeaderEntryMatcher.find();
            while (matchFound) {
                var uri = paginationHeaderEntryMatcher.group("uri");
                var rel = paginationHeaderEntryMatcher.group("rel");
                switch (rel) {
                    case "first":
                        first = URI.create(uri);
                        break;
                    case "prev":
                        previous = URI.create(uri);
                        break;
                    case "next":
                        next = URI.create(uri);
                        break;
                    case "last":
                        last = URI.create(uri);
                        break;
                }
                matchFound = paginationHeaderEntryMatcher.find();
            }
        }
        return new PaginationLinks(first, previous, next, last);
    }
}
