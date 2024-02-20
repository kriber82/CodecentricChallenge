package de.kristianbergmann.codecentricchallengebackend.application.datamodel;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ForGettingDeveloperProfiles;

import java.util.ArrayList;
import java.util.List;

public class DeveloperProfilesInMemory implements ForGettingDeveloperProfiles {
    private List<Developer> developers = new ArrayList<>();

    @Override
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

}
