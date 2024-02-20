package de.kristianbergmann.codecentricchallengebackend.application;

import java.util.List;

public record SourceCodeRepository(String name, List<SourceCodeLanguage> languages) {
}
