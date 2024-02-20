package de.kristianbergmann.codecentricchallengebackend.application;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;

import java.util.Collections;
import java.util.List;

public class DisplayDeveloperLanguageProficiency {
    private final ForGettingDeveloperProfiles forGettingDeveloperProfiles;
    private final ForShowingDeveloperProficiencies forShowingDeveloperProfiles;

    public DisplayDeveloperLanguageProficiency(ForGettingDeveloperProfiles forGettingDeveloperProfiles, ForShowingDeveloperProficiencies forShowingDeveloperProfiles) {
        this.forGettingDeveloperProfiles = forGettingDeveloperProfiles;
        this.forShowingDeveloperProfiles = forShowingDeveloperProfiles;
    }

    public List<Developer> queryDeveloperProfiles() {
        return forGettingDeveloperProfiles.getDevelopers();
    }

    public void showDeveloperProfiles() {
        DeveloperLanguageProficiencies proficiencies = new DeveloperLanguageProficiencies(Collections.emptyList());
        forShowingDeveloperProfiles.show(proficiencies);
    }
}
