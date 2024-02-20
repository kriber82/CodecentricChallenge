package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.ForGettingDeveloperProfiles;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCodecentricDeveloperProfilesFromGithub implements ForGettingDeveloperProfiles {
    @Override
    public List<Developer> getDevelopers() {

        RestClient restClient = RestClient.create();

        GithubProfileJson[] response = restClient.get()
                .uri("https://api.github.com/orgs/codecentric/members")
                .retrieve()
                .body(GithubProfileJson[].class);

        var result = Arrays.stream(response).map(p -> new Developer(p.login, Collections.emptyList())).toList(); //TODO use display name instead of login

        return result;
    }
}
