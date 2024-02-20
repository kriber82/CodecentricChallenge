package de.kristianbergmann.codecentricchallengebackend.application.datamodel;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.SourceCodeLanguage;

import java.util.List;

public record SourceCodeRepository(String name, List<SourceCodeLanguage> languages) {
}
