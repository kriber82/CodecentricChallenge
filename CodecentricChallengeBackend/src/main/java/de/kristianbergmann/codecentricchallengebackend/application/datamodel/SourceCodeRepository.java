package de.kristianbergmann.codecentricchallengebackend.application.datamodel;

import java.util.List;

public record SourceCodeRepository(String name, List<ProgrammingLanguage> languages) {
}
