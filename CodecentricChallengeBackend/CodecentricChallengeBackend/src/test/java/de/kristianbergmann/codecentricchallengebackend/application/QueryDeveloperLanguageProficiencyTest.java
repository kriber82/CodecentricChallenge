package de.kristianbergmann.codecentricchallengebackend.application;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryDeveloperLanguageProficiencyTest {
    @Test
    public void returnsEmptyDevelopersWithEmptyAnswerFromPort() {
        ForGettingDeveloperProfiles forGettingDeveloperProfiles = new DeveloperProfilesInMemory();
        QueryDeveloperLanguageProficiency tested = new QueryDeveloperLanguageProficiency(forGettingDeveloperProfiles);
        List<Developer> developers = tested.queryDeveloperProfiles();
        assertThat(developers.size()).isEqualTo(0);
    }

}
