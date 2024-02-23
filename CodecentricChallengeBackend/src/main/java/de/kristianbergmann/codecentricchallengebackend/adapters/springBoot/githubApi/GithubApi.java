package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class GithubApi {

    private final static Logger logger = LoggerFactory.getLogger(GithubApi.class);

    private final String githubBearerToken;

    public GithubApi() {
        this.githubBearerToken = System.getenv("GITHUB_API_TOKEN");
        if (this.githubBearerToken == null)
            logger.warn("GITHUB_API_TOKEN not found. Github API requests will be rate-limited.\nPlease store a Github API token in the environment variable GITHUB_API_TOKEN.");
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

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(objectType);
    }

    private <T> PaginatedResult<T> getSinglePage(URI url, Class<T> elementType) {
        RestClient restClient = buildAuthorizingClient();

        @SuppressWarnings("unchecked")
        Class<T[]> bodyType = (Class<T[]>) elementType.arrayType();
        var response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(bodyType);
        var paginationLinks = PaginationLinks.parseFromLinkHeader(response.getHeaders().getFirst("link"));
        var body = response.getBody();
        return new PaginatedResult<>(body, paginationLinks);
    }

    private <T> T[] getAllPages(String url, Class<T> elementType) {
        Stream<T> resultStream = Stream.empty();
        var nextUrl = URI.create(url);
        while (nextUrl != null) {
            var currentPage = getSinglePage(nextUrl, elementType);
            resultStream = Stream.concat(resultStream, Arrays.stream(currentPage.payload()));
            nextUrl = currentPage.paginationLinks().next();
        }
        return resultStream.toArray(size -> newArrayInstance(elementType, size));
    }

    private RestClient buildAuthorizingClient() {
        var builder = RestClient.builder();
        if (githubBearerToken != null)
            builder.defaultHeader("Authorization", "Bearer " + githubBearerToken);
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private <T> T[] newArrayInstance(Class<T> elementType, int size) {
        return (T[])Array.newInstance(elementType, size);
    }

}
