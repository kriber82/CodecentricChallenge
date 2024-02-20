package de.kristianbergmann.codecentricchallengebackend.application;

import java.util.List;

public class DisplayDeveloperLanguageProficiency {
    private final ForGettingDeveloperProfiles forGettingDeveloperProfiles;

    public DisplayDeveloperLanguageProficiency(ForGettingDeveloperProfiles forGettingDeveloperProfiles) {
        this.forGettingDeveloperProfiles = forGettingDeveloperProfiles;
    }

    public List<Developer> queryDeveloperProfiles() {
        return forGettingDeveloperProfiles.getDevelopers();
    }
}
