package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCodecentricDeveloperProfilesFromGithubTest {
    private GetCodecentricDeveloperProfilesFromGithub tested;

    @BeforeEach
    public void setup() {
        tested = new GetCodecentricDeveloperProfilesFromGithub();
    }

    @Test
    public void queriesOrganizationMembersFromGithub() {
        var developers = tested.getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        assertThat(developers.length).isEqualTo(30);
        assertThat(developers).anyMatch(d -> d.login.equals("danielbayerlein") && d.repos_url.equals("https://api.github.com/users/danielbayerlein/repos"));
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

    //@Test
    public void queriesCodecentricDevelopersFromGithub() {
        //TODO hits API rate limit too fast
        var developers = tested.getDevelopers();
        assertThat(developers.size()).isEqualTo(30);

        var dennisEffing = developers.stream().filter(d -> d.name().equals("denniseffing")).findFirst().get();
        assertThat(dennisEffing.repositories().size()).isEqualTo(30);

        var istioChaosDemo = dennisEffing.repositories().stream().filter(r -> r.name().equals("istio-chaos-demo")).findFirst().get();
        assertThat(istioChaosDemo.languages()).contains(new ProgrammingLanguage("java"), new ProgrammingLanguage("dockerfile"));
    }

}
