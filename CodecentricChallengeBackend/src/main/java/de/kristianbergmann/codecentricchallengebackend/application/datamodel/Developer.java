package de.kristianbergmann.codecentricchallengebackend.application.datamodel;

import java.util.List;

public record Developer(String pseudonym, List<SourceCodeRepository> repositories) { }
