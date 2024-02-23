package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCodecentricDeveloperProfilesFromGithubTest {
    private GetCodecentricDeveloperProfilesFromGithub tested;

    @BeforeEach
    public void setup() {
        tested = new GetCodecentricDeveloperProfilesFromGithub(2);
    }

    @Test
    public void queriesOrganizationMembersFromGithub() {
        var answer = tested.getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        var developers = answer.payload();
        assertThat(developers.length).isEqualTo(30);
        assertThat(developers).anyMatch(d -> d.login.equals("danielbayerlein") && d.repos_url.equals("https://api.github.com/users/danielbayerlein/repos"));
    }

    @Test
    @Disabled("later")
    public void foo() {
        PaginatedResult<GithubProfileJson> answer = tested.getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        assertThat(answer.nextPageUri()).isNotNull();
        /*
        tested.getOrganizationMembers(answer.nextPageUri);
        assertThat(developers.length).isEqualTo(30);
        assertThat(developers).anyMatch(d -> d.login.equals("danielbayerlein") && d.repos_url.equals("https://api.github.com/users/danielbayerlein/repos"));

         */
    }

    @Test
    public void parsesPaginationHeaders() {
        PaginatedResult<GithubProfileJson> answer = tested.getPaginatedResult("https://api.github.com/orgs/codecentric/members");
        assertThat(answer.nextPageUri()).isNotNull();
    }

    @Test
    public void queriesUserReposFromGithub() {
        var repositories = tested.getRepositories("https://api.github.com/users/denniseffing/repos");
        assertThat(repositories.length).isEqualTo(30);
        assertThat(repositories).anyMatch(r -> r.name.equals("istio-chaos-demo") && r.languages_url.equals("https://api.github.com/repos/denniseffing/istio-chaos-demo/languages"));
    }

    @Test
    public void queriesRepoLanguagesFromGithub() {
        var languages = tested.getLanguages("https://api.github.com/repos/denniseffing/istio-chaos-demo/languages");
        assertThat(languages.size()).isEqualTo(2);
        assertThat(languages.stream().map(ProgrammingLanguage::name)).contains("Java", "Dockerfile");
    }

    @Test
    public void compilesDatamodelForUsecaseUsingGithubQueriesInternally() {
        //note that child requests are limited to 2 in setup

        var developers = tested.getDevelopers();

        assertThat(developers.size()).isEqualTo(2);

        var dev = developers.stream().filter(d -> d.name().equals("danielbayerlein")).findFirst().get();
        assertThat(dev.repositories().size()).isEqualTo(2);

        var repo = dev.repositories().stream().filter(r -> r.name().equals("amplify-js")).findFirst().get();
        assertThat(repo.languages().size()).isEqualTo(10);
        assertThat(repo.languages().stream().map(ProgrammingLanguage::name)).contains("TypeScript", "HTML");
    }

}
