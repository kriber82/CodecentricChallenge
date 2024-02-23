package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot;

import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi.*;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ForGettingDeveloperProfiles;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;

import java.util.*;

public class GetCodecentricDeveloperProfilesFromGithub implements ForGettingDeveloperProfiles {

    private final GithubApi githubApi;
    private final int maxChildRequests;

    public GetCodecentricDeveloperProfilesFromGithub() {
        this(new GithubApi(), Integer.MAX_VALUE);
    }

    public GetCodecentricDeveloperProfilesFromGithub(GithubApi githubApi, int maxChildRequests) {
        this.githubApi = githubApi;
        this.maxChildRequests = maxChildRequests;
    }

    @Override
    public List<Developer> getDevelopers() {

        GithubProfileJson[] profiles = githubApi.getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        return Arrays.stream(profiles).
                limit(maxChildRequests).
                parallel().
                map(p -> toDeveloper(p, maxChildRequests)).
                toList();
    }

    private Developer toDeveloper(GithubProfileJson p, int maxChildRequests) {
        var repos = Arrays.stream(githubApi.getRepositories(p.repos_url())).
                limit(maxChildRequests).
                parallel().
                map(this::toRepo).
                toList();
        return new Developer(p.login(), repos); //TODO use display name instead of login
    }

    private SourceCodeRepository toRepo(GithubRepositoryJson r) {
        var languages = githubApi.getLanguages(r.languages_url());
        return new SourceCodeRepository(r.name(), languages);
    }

}
