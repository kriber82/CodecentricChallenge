package de.kristianbergmann.codecentricchallengebackend.application.viewmodel;

import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.ForShowingDeveloperProficiencies;

public class ShowDeveloperProficienciesDummy implements ForShowingDeveloperProficiencies {
    public DeveloperLanguageProficiencies lastShown = null;

    @Override
    public void show(DeveloperLanguageProficiencies shown) {
        lastShown = shown;
    }
}
