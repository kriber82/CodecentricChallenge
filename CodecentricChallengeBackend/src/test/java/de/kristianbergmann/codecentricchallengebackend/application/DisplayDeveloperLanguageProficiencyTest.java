package de.kristianbergmann.codecentricchallengebackend.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DisplayDeveloperLanguageProficiencyTest {

    private final SourceCodeLanguage java = new SourceCodeLanguage("java");
    private final SourceCodeLanguage typescript = new SourceCodeLanguage("typescript");
    private final List<SourceCodeLanguage> myRepoLanguages = Arrays.asList(java, typescript);
    private final SourceCodeRepository myRepo = new SourceCodeRepository("my-repo", myRepoLanguages);
    private final Developer meDeveloper = new Developer("me", Arrays.asList(myRepo));

    private DisplayDeveloperLanguageProficiency tested;
    private DeveloperProfilesInMemory forGettingDeveloperProfiles;

    @BeforeEach
    public void setup() {
        forGettingDeveloperProfiles = new DeveloperProfilesInMemory();
        tested = new DisplayDeveloperLanguageProficiency(forGettingDeveloperProfiles);
    }

    @Test
    public void returnsEmptyDevelopersWithEmptyAnswerFromPort() {
        List<Developer> developers = tested.queryDeveloperProfiles();
        assertThat(developers.size()).isEqualTo(0);
    }

    @Test
    public void returnsDevelopersGivenByPort() {
        forGettingDeveloperProfiles.setDevelopers(Arrays.asList(meDeveloper));
        List<Developer> developers = tested.queryDeveloperProfiles();
        assertThat(developers).containsExactly(meDeveloper);
        assertThat(developers.get(0).repositories()).containsExactly(myRepo);
        assertThat(developers.get(0).repositories().get(0).languages()).containsExactly(java, typescript);
    }


}
