package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.ForGettingDeveloperProfiles;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.*;

public class GetCodecentricDeveloperProfilesFromGithub implements ForGettingDeveloperProfiles {

    @Override
    public List<Developer> getDevelopers() {

        GithubProfileJson[] profiles = getOrganizationMembers("https://api.github.com/orgs/codecentric/members");

        //TODO parallel requests (needs authentication)
        //var result = Arrays.stream(profiles).parallel().map(p -> new Developer(p.login, getRepositories(p))).toList(); //TODO use display name instead of login

        //return Arrays.stream(response).map(r -> new SourceCodeRepository(r.name, getLanguages(r))).toList();

        //return result;

        return Collections.emptyList();
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
