package de.kristianbergmann.codecentricchallengebackend.application.viewmodel;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;

import java.util.Map;

public record DeveloperLanguageProficiency(String developerName, Map<ProgrammingLanguage, Integer> repoCountByLanguage) {
}
