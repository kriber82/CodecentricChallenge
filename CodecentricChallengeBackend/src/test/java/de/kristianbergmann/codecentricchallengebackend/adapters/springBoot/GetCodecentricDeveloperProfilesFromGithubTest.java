package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot;

import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi.GithubApi;
import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi.GithubProfileJson;
import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi.GithubRepositoryJson;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCodecentricDeveloperProfilesFromGithubTest {
    private GithubApi githubApiMock;
    private GetCodecentricDeveloperProfilesFromGithub tested;

    @BeforeEach
    public void setup() {
        githubApiMock = mock(GithubApi.class);
        tested = new GetCodecentricDeveloperProfilesFromGithub(githubApiMock, 2);
    }

    @Test
    public void compilesDatamodelForUsecaseFromGithubApi() {
        GithubProfileJson[] developersResponse = new GithubProfileJson[] {
                new GithubProfileJson("danielbayerlein", "https://api.github.com/users/danielbayerlein/repos"),
                new GithubProfileJson("someone_else", "someone_else_repo_url")};
        when(githubApiMock.getOrganizationMembers("https://api.github.com/orgs/codecentric/members")).thenReturn(developersResponse);

        when(githubApiMock.getRepositories(anyString())).thenReturn(new GithubRepositoryJson[0]);
        when(githubApiMock.getRepositories("https://api.github.com/users/danielbayerlein/repos")).thenReturn(new GithubRepositoryJson[] {
                new GithubRepositoryJson("amplify-js", "https://api.github.com/repos/denniseffing/amplify-js/languages"),
                new GithubRepositoryJson("other_repo", "other_repo_url")
        });

        when(githubApiMock.getLanguages(anyString())).thenReturn(List.of());
        when(githubApiMock.getLanguages("https://api.github.com/repos/denniseffing/amplify-js/languages")).thenReturn(Arrays.asList(
            new ProgrammingLanguage("TypeScript"),
            new ProgrammingLanguage("HTML")
        ));

        var developers = tested.getDevelopers();
        assertThat(developers.stream().map(Developer::name)).containsExactly("danielbayerlein", "someone_else");

        var dev = developers.stream().filter(d -> d.name().equals("danielbayerlein")).findFirst().get();
        assertThat(dev.repositories().stream().map(SourceCodeRepository::name)).containsExactly("amplify-js", "other_repo");

        var repo = dev.repositories().stream().filter(r -> r.name().equals("amplify-js")).findFirst().get();
        assertThat(repo.languages().stream().map(ProgrammingLanguage::name)).containsExactly("TypeScript", "HTML");
    }

}
