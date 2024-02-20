package de.kristianbergmann.codecentricchallengebackend.application;

import java.util.ArrayList;
import java.util.List;

public class DeveloperProfilesInMemory implements ForGettingDeveloperProfiles {
    private List<Developer> developers = new ArrayList<>();

    @Override
    public List<Developer> getDevelopers() {
        return developers;
    }

    void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

}
