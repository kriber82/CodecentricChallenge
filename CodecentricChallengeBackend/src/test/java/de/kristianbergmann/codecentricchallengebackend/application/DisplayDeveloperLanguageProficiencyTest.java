package de.kristianbergmann.codecentricchallengebackend.application;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DisplayDeveloperLanguageProficiencyTest {

    private final SourceCodeLanguage java = new SourceCodeLanguage("java");
    private final SourceCodeLanguage typescript = new SourceCodeLanguage("typescript");
    private final List<SourceCodeLanguage> kristiansRepoLanguages = Arrays.asList(java, typescript);
    private final SourceCodeRepository kristiansRepo = new SourceCodeRepository("my-repo", kristiansRepoLanguages);
    private final Developer kristian = new Developer("Kristian", Arrays.asList(kristiansRepo));

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

    @Test
    public void showsDeveloperLanguagesForSingleDevAndRepo() {
        forGettingDeveloperProfiles.setDevelopers(List.of(kristian));

        tested.showDeveloperProfiles();

        var shown = forShowingDeveloperProfiles.lastShown;
        assertThat(shown.developers().size()).isEqualTo(1);
        
        var kristiansProficiency = shown.developers().get(0);
        assertThat(kristiansProficiency.developerName()).isEqualTo("Kristian");

        var kristiansLanguageCounts = kristiansProficiency.repoCountByLanguage();
        assertThat(kristiansLanguageCounts.size()).isEqualTo(2);
        assertThat(kristiansLanguageCounts.get(java)).isEqualTo(1);
        assertThat(kristiansLanguageCounts.get(typescript)).isEqualTo(1);
    }

    //TODO several repos with same language
    //TODO users without repos

    private static class ShowDeveloperProficienciesDummy implements ForShowingDeveloperProficiencies {
        public DeveloperLanguageProficiencies lastShown = null;

        @Override
        public void show(DeveloperLanguageProficiencies shown) {
            lastShown = shown;
        }
    }
}
