package de.kristianbergmann.codecentricchallengebackend.application;

import java.util.Collections;
import java.util.List;

public class QueryDeveloperLanguageProficiency {
    private final ForGettingDeveloperProfiles forGettingDeveloperProfiles;

    public QueryDeveloperLanguageProficiency(ForGettingDeveloperProfiles forGettingDeveloperProfiles) {
        this.forGettingDeveloperProfiles = forGettingDeveloperProfiles;
    }

    public List<Developer> queryDeveloperProfiles() {
        return forGettingDeveloperProfiles.getDevelopers();
    }
}
