package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import java.net.URI;

public record PaginatedResult<T>(T[] payload, URI nextPageUri) {
}
