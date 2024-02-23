package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

public record PaginatedResult<T>(T[] payload, PaginationLinks paginationLinks) {
}
