package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi;

public record PaginatedResult<T>(T[] payload, PaginationLinks paginationLinks) {
}
