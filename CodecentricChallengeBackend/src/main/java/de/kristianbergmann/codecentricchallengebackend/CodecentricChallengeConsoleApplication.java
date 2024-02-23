package de.kristianbergmann.codecentricchallengebackend;

import de.kristianbergmann.codecentricchallengebackend.adapters.consoleUi.ShowDeveloperProficienciesInConsole;
import de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.GetCodecentricDeveloperProfilesFromGithub;
import de.kristianbergmann.codecentricchallengebackend.application.DisplayDeveloperLanguageProficiencyUseCase;

public class CodecentricChallengeConsoleApplication {
    public static void main(String[] args) {
        var getDeveloperProfilesAdapter = new GetCodecentricDeveloperProfilesFromGithub();
        var showDeveloperProfilesAdapter = new ShowDeveloperProficienciesInConsole();
        var useCase = new DisplayDeveloperLanguageProficiencyUseCase(getDeveloperProfilesAdapter, showDeveloperProfilesAdapter);
        useCase.showDeveloperProfiles();
    }
}
