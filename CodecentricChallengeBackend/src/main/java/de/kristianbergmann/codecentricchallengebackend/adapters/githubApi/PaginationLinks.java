package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import java.net.URI;

public record PaginationLinks(URI first, URI previous, URI next, URI last) {
}
