package de.kristianbergmann.codecentricchallengebackend.adapters.consoleUi;

import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.ForShowingDeveloperProficiencies;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;

public class ShowDeveloperProficienciesInConsole implements ForShowingDeveloperProficiencies {

    @Override
    public void show(DeveloperLanguageProficiencies shown) {
        System.out.println(renderShownString(shown));
    }

    public String renderShownString(DeveloperLanguageProficiencies shown) {
        StringBuilder result = new StringBuilder();

        for (var dev: shown.developers()) {
            result.append("- " + dev.developerName() + "\n");
            for (var language: dev.repoCountByLanguage().entrySet()) {
                result.append("    " + language.getKey().name() + " " + language.getValue() + "\n");
            }
        }
        return result.toString();
    }
}
