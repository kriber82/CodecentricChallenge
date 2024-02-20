package de.kristianbergmann.codecentricchallengebackend.application;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.Developer;
import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeLanguage;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiencies;
import de.kristianbergmann.codecentricchallengebackend.application.viewmodel.DeveloperLanguageProficiency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Developer> developers = queryDeveloperProfiles();
        List<DeveloperLanguageProficiency> proficiencies = new ArrayList<>();
        for (var dev: developers) {
            Map<SourceCodeLanguage, Integer> repoCountsByLanguage = new HashMap<>();
            for (var repo: dev.repositories()) {
                for (var lang: repo.languages()) {
                    if (!repoCountsByLanguage.containsKey(lang))
                        repoCountsByLanguage.put(lang, 0);
                    repoCountsByLanguage.put(lang, repoCountsByLanguage.get(lang) + 1);
                }
            }
            proficiencies.add(new DeveloperLanguageProficiency(dev.name(), repoCountsByLanguage));
        }
        forShowingDeveloperProfiles.show(new DeveloperLanguageProficiencies(proficiencies));
    }

}
