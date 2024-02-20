package de.kristianbergmann.codecentricchallengebackend.application;

import java.util.Arrays;
import java.util.List;

public record Developer(String pseudonym, List<SourceCodeRepository> repositories) { }
