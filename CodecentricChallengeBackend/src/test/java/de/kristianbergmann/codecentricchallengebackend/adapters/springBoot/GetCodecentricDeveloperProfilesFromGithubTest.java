package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot;

import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi.GithubApi;
import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi.PaginationLinks;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCodecentricDeveloperProfilesFromGithubTest {
    private GetCodecentricDeveloperProfilesFromGithub tested;

    @BeforeEach
    public void setup() {
        tested = new GetCodecentricDeveloperProfilesFromGithub(new GithubApi(), 2);
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
