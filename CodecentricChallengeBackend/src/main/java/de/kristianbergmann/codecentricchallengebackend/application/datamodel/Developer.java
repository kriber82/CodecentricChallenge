package de.kristianbergmann.codecentricchallengebackend.application.datamodel;

import java.util.List;

public record Developer(String name, List<SourceCodeRepository> repositories) { }
