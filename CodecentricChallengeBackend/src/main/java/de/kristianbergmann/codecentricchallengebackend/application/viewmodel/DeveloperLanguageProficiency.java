package de.kristianbergmann.codecentricchallengebackend.application.viewmodel;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeLanguage;

import java.util.Map;

public record DeveloperLanguageProficiency(String developerName, Map<SourceCodeLanguage, Integer> repoCountByLanguage) {
}
