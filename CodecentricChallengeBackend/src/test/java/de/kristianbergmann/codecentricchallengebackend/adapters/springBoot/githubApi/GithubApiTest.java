package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GithubApiTest {

    private GithubApi tested;

    @BeforeEach
    public void setup() {
        tested = new GithubApi();
    }

    @Test
    public void queriesOrganizationMembersFromGithub() {
        var developers = tested.getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        assertThat(developers.length).withFailMessage("Probably only first page was retrieved").isGreaterThan(30);
        assertThat(developers).anyMatch(d -> d.login().equals("danielbayerlein") && d.repos_url().equals("https://api.github.com/users/danielbayerlein/repos"));
        assertThat(developers).anyMatch(d -> d.login().equals("ufried"));
    }

    @Test
    public void queriesUserReposFromGithub() {
        var repositories = tested.getRepositories("https://api.github.com/users/denniseffing/repos");
        assertThat(repositories.length).withFailMessage("Probably only first page was retrieved").isGreaterThan(30);
        assertThat(repositories).anyMatch(r -> r.name().equals("istio-chaos-demo") && r.languages_url().equals("https://api.github.com/repos/denniseffing/istio-chaos-demo/languages"));
        assertThat(repositories).anyMatch(r -> r.name().equals("titanium-server"));
    }

    @Test
    public void queriesRepoLanguagesFromGithub() {
        var languages = tested.getLanguages("https://api.github.com/repos/denniseffing/istio-chaos-demo/languages");
        assertThat(languages.size()).isEqualTo(2);
        assertThat(languages.stream().map(ProgrammingLanguage::name)).contains("Java", "Dockerfile");
    }
}
