package de.kristianbergmann.codecentricchallengebackend.application;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
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
    private ShowDeveloperProficienciesDummy forShowingDeveloperProfiles;

    @BeforeEach
    public void setup() {
        forGettingDeveloperProfiles = new DeveloperProfilesInMemory();
        forShowingDeveloperProfiles = new ShowDeveloperProficienciesDummy();
        tested = new DisplayDeveloperLanguageProficiency(forGettingDeveloperProfiles, forShowingDeveloperProfiles);
    }

    @Test
    public void showsEmptyProficiencyTableWithEmptyAnswerFromPort() {
        //no developers in forGettingDeveloperProfiles
        tested.showDeveloperProfiles();
        assertThat(forShowingDeveloperProfiles.lastShown).isEqualTo(new DeveloperLanguageProficiencies(Collections.emptyList()));
    }

    //TODO refactor to test of showDeveloperProfiles
    @Test
    public void returnsDevelopersGivenByPort() {
        forGettingDeveloperProfiles.setDevelopers(Arrays.asList(meDeveloper));
        List<Developer> developers = tested.queryDeveloperProfiles();
        assertThat(developers).containsExactly(meDeveloper);
        assertThat(developers.get(0).repositories()).containsExactly(myRepo);
        assertThat(developers.get(0).repositories().get(0).languages()).containsExactly(java, typescript);
    }

    private static class ShowDeveloperProficienciesDummy implements ForShowingDeveloperProficiencies {
        public DeveloperLanguageProficiencies lastShown = null;

        @Override
        public void show(DeveloperLanguageProficiencies shown) {
            lastShown = shown;
        }
    }
}
