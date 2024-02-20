package de.kristianbergmann.codecentricchallengebackend.application;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.DeveloperProfilesInMemory;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeRepository;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.ShowDeveloperProficienciesDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DisplayDeveloperLanguageProficiencyUseCaseTest {

    private DisplayDeveloperLanguageProficiencyUseCase tested;
    private DeveloperProfilesInMemory forGettingDeveloperProfiles;
    private ShowDeveloperProficienciesDummy forShowingDeveloperProfiles;

    private final ProgrammingLanguage java = new ProgrammingLanguage("java");
    private final ProgrammingLanguage typescript = new ProgrammingLanguage("typescript");
    private final ProgrammingLanguage php = new ProgrammingLanguage("PHP");

    private final List<ProgrammingLanguage> javaAndTypescriptLanguages = Arrays.asList(java, typescript);
    private final SourceCodeRepository javaAndTypescriptRepo = new SourceCodeRepository("j-and-t-repo", javaAndTypescriptLanguages);

    private final List<ProgrammingLanguage> javaAndPhpLanguages = Arrays.asList(java, php);
    private final SourceCodeRepository javaAndPhpRepo = new SourceCodeRepository("j-and-p-repo", javaAndPhpLanguages);

    @BeforeEach
    public void setup() {
        forGettingDeveloperProfiles = new DeveloperProfilesInMemory();
        forShowingDeveloperProfiles = new ShowDeveloperProficienciesDummy();
        tested = new DisplayDeveloperLanguageProficiencyUseCase(forGettingDeveloperProfiles, forShowingDeveloperProfiles);
    }

    @Test
    public void showsEmptyProficiencyTableWithEmptyAnswerFromPort() {
        //no developers in forGettingDeveloperProfiles
        tested.showDeveloperProfiles();
        assertThat(forShowingDeveloperProfiles.lastShown).isEqualTo(new DeveloperLanguageProficiencies(Collections.emptyList()));
    }

    @Test
    public void showsDeveloperLanguagesForSingleDevAndRepo() {
        var singleRepoDev = new Developer("A Dev", Arrays.asList(javaAndTypescriptRepo));
        forGettingDeveloperProfiles.setDevelopers(List.of(singleRepoDev));

        tested.showDeveloperProfiles();

        var languageCounts = forShowingDeveloperProfiles.lastShown.developers().get(0).repoCountByLanguage();
        assertThat(languageCounts.size()).isEqualTo(2);
        assertThat(languageCounts.get(java)).isEqualTo(1);
        assertThat(languageCounts.get(typescript)).isEqualTo(1);
    }

    @Test
    public void aggregatesLanguagesFromMultipleReposOfSameUser() {
        var multiRepoDev = new Developer("A Dev", Arrays.asList(javaAndTypescriptRepo, javaAndPhpRepo));
        forGettingDeveloperProfiles.setDevelopers(List.of(multiRepoDev));

        tested.showDeveloperProfiles();

        var languageCounts = forShowingDeveloperProfiles.lastShown.developers().get(0).repoCountByLanguage();
        assertThat(languageCounts.size()).isEqualTo(3);
        assertThat(languageCounts.get(java)).isEqualTo(2);
        assertThat(languageCounts.get(typescript)).isEqualTo(1);
        assertThat(languageCounts.get(php)).isEqualTo(1);
    }

    @Test
    public void containsOneProficiencyEntryPerDeveloper() {
        var dev1 = new Developer("Dev 1", Arrays.asList(javaAndTypescriptRepo));
        var dev2 = new Developer("Dev 2", Arrays.asList(javaAndPhpRepo));
        forGettingDeveloperProfiles.setDevelopers(List.of(dev1, dev2));

        tested.showDeveloperProfiles();

        var shown = forShowingDeveloperProfiles.lastShown;
        assertThat(shown.developers().stream().map(d -> d.developerName()).toList()).
                containsExactly("Dev 1", "Dev 2");
    }

    @Test
    public void discardsDevelopersWithoutLanguageProficiencies() {
        var noRepoDev = new Developer("A Dev", Collections.emptyList());
        forGettingDeveloperProfiles.setDevelopers(List.of(noRepoDev));

        tested.showDeveloperProfiles();

        var shown = forShowingDeveloperProfiles.lastShown;
        assertThat(shown.developers().size()).isEqualTo(0);
    }

}
