package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ForGettingDeveloperProfiles;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.*;

public class GetCodecentricDeveloperProfilesFromGithub implements ForGettingDeveloperProfiles {

    private final int maxChildRequests;

    public GetCodecentricDeveloperProfilesFromGithub() {
        this(Integer.MAX_VALUE);
    }

    public GetCodecentricDeveloperProfilesFromGithub(int maxChildRequests) {
        this.maxChildRequests = maxChildRequests;
    }

    @Override
    public List<Developer> getDevelopers() {

        GithubProfileJson[] profiles = getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        return Arrays.stream(profiles).limit(maxChildRequests).parallel().map(p -> toDeveloper(p, maxChildRequests)).toList();
    }

    private Developer toDeveloper(GithubProfileJson p, int maxChildRequests) {
        var repos = Arrays.stream(getRepositories(p.repos_url)).limit(maxChildRequests).parallel().map(this::toRepo).toList();
        return new Developer(p.login, repos); //TODO use display name instead of login
    }

    private SourceCodeRepository toRepo(GithubRepositoryJson r) {
        var languages = getLanguages(r.languages_url);
        return new SourceCodeRepository(r.name, languages);
    }

    public GithubProfileJson[] getOrganizationMembers(String organizationMembersUrl) {
        RestClient restClient = buildAuthorizingClient();

        return restClient.get()
                .uri(organizationMembersUrl)
                .retrieve()
                .body(GithubProfileJson[].class);
    }

    public GithubRepositoryJson[] getRepositories(String profileReposUrl) {
        RestClient restClient = buildAuthorizingClient();

        return restClient.get()
                .uri(profileReposUrl)
                .retrieve()
                .body(GithubRepositoryJson[].class);
    }

    public List<ProgrammingLanguage> getLanguages(String repoLanguagesUrl) {
        RestClient restClient = buildAuthorizingClient();

        Map<String, Integer> response = restClient.get()
                .uri(repoLanguagesUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Integer>>() {});

        return response.keySet().stream().map(ProgrammingLanguage::new).toList();
    }

    private static RestClient buildAuthorizingClient() {
        return RestClient.builder().
                defaultHeader("Authorization", "Bearer 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").
                build();
    }

}
