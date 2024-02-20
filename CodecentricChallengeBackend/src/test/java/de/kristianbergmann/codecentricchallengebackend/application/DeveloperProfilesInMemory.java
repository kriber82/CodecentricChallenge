package de.kristianbergmann.codecentricchallengebackend.application;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;

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
