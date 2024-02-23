package de.kristianbergmann.codecentricchallengebackend.application.viewmodel;

public class ShowDeveloperProficienciesMock implements ForShowingDeveloperProficiencies {
    public DeveloperLanguageProficiencies lastShown = null;

    @Override
    public void show(DeveloperLanguageProficiencies shown) {
        lastShown = shown;
    }
}
