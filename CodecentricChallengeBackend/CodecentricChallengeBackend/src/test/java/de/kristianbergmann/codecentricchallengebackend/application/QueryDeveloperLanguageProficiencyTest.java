package de.kristianbergmann.codecentricchallengebackend.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryDeveloperLanguageProficiencyTest {

    private QueryDeveloperLanguageProficiency tested;
    private DeveloperProfilesInMemory forGettingDeveloperProfiles;

    @BeforeEach
    public void setup() {
        forGettingDeveloperProfiles = new DeveloperProfilesInMemory();
        tested = new QueryDeveloperLanguageProficiency(forGettingDeveloperProfiles);
    }

    @Test
    public void returnsEmptyDevelopersWithEmptyAnswerFromPort() {
        List<Developer> developers = tested.queryDeveloperProfiles();
        assertThat(developers.size()).isEqualTo(0);
    }

    @Test
    public void returnsDevelopersGivenByPort() {
        Developer meDeveloper = new Developer("Me");
        forGettingDeveloperProfiles.setDevelopers(Arrays.asList(meDeveloper));
        List<Developer> developers = tested.queryDeveloperProfiles();
        assertThat(developers).containsExactly(meDeveloper);
    }

}
