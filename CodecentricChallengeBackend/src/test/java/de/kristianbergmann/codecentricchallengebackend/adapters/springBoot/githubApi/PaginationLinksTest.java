package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginationLinksTest {

    @Test
    public void parsesPaginationHeadersWithNextPage() throws URISyntaxException {
        var parsedHeaders = PaginationLinks.parseFromLinkHeader("<https://api.github.com/organizations/1009716/members?page=2>; rel=\"next\", <https://api.github.com/organizations/1009716/members?page=2>; rel=\"last\"");
        assertThat(parsedHeaders.first()).isNull();
        assertThat(parsedHeaders.previous()).isNull();
        assertThat(parsedHeaders.next()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=2"));
        assertThat(parsedHeaders.last()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=2"));
    }

    @Test
    public void parsesPaginationHeadersWithPreviousPage() throws URISyntaxException {
        var parsedHeaders = PaginationLinks.parseFromLinkHeader("<https://api.github.com/organizations/1009716/members?page=1>; rel=\"prev\", <https://api.github.com/organizations/1009716/members?page=1>; rel=\"first\"");
        assertThat(parsedHeaders.first()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=1"));
        assertThat(parsedHeaders.previous()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=1"));
        assertThat(parsedHeaders.next()).isNull();
        assertThat(parsedHeaders.last()).isNull();
    }

    @Test
    public void returnsEmptyPaginationHeaderForAbsentHeader() {
        var parsedHeaders = PaginationLinks.parseFromLinkHeader(null);
        assertThat(parsedHeaders).isEqualTo(new PaginationLinks(null, null, null, null));
    }

}
