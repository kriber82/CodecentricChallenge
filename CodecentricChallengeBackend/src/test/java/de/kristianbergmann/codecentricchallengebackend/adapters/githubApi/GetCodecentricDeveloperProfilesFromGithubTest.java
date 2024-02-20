package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCodecentricDeveloperProfilesFromGithubTest {
    @Test
    public void queriesCodecentricDevelopersFromGithub() {
        GetCodecentricDeveloperProfilesFromGithub tested = new GetCodecentricDeveloperProfilesFromGithub();

        var developers = tested.getDevelopers();

        assertThat(developers.size()).isEqualTo(30);

        //var dennisEffing = developers.stream().filter(d -> d.name().equals("denniseffing")).findFirst().get();
        //assertThat(dennisEffing.repositories().size()).isEqualTo(99);
    }
}
