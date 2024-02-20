package de.kristianbergmann.codecentricchallengebackend.adapters.consoleUi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiency;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowDeveloperProficienciesInConsoleTest {
    @Test
    public void rendersEmptyStringForEmptyProfiles() {
        var tested = new ShowDeveloperProficienciesInConsole();
        var renderedString = tested.renderShownString(new DeveloperLanguageProficiencies(Collections.emptyList()));
        assertThat(renderedString).isEmpty();
    }

    @Test
    public void rendersStringForProfiles() {
        var tested = new ShowDeveloperProficienciesInConsole();

        var dev1Languages = new HashMap<ProgrammingLanguage, Integer>() {{
            put(new ProgrammingLanguage("Java"), 2);
            put(new ProgrammingLanguage("C++"), 1);
        }};
        DeveloperLanguageProficiency dev1ViewModel = new DeveloperLanguageProficiency("Dev 1", dev1Languages);

        var dev2Languages = new HashMap<ProgrammingLanguage, Integer>() {{
            put(new ProgrammingLanguage("TypeScript"), 5);
        }};
        DeveloperLanguageProficiency dev2ViewModel = new DeveloperLanguageProficiency("Dev 2", dev2Languages);

        DeveloperLanguageProficiencies viewModel = new DeveloperLanguageProficiencies(Arrays.asList(dev1ViewModel, dev2ViewModel));
        var renderedString = tested.renderShownString(viewModel);
        assertThat(renderedString).isEqualTo("""
                - Dev 1
                    Java 2
                    C++ 1
                - Dev 2
                    TypeScript 5
                """);
    }
}
