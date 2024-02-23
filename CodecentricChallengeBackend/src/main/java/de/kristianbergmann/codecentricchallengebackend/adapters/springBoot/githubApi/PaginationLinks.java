package de.kristianbergmann.codecentricchallengebackend.adapters.springBoot.githubApi;

import java.net.URI;
import java.util.regex.Pattern;

public record PaginationLinks(URI first, URI previous, URI next, URI last) {
    private static final Pattern paginationHeaderEntryPattern = Pattern.compile("<(?<uri>[^>]*)>; rel=\"(?<rel>[^\"]*)\"");

    public static PaginationLinks parseFromLinkHeader(String linkHeader) {
        URI first = null;
        URI previous = null;
        URI next = null;
        URI last = null;
        if (linkHeader != null) {
            var paginationHeaderEntryMatcher = paginationHeaderEntryPattern.matcher(linkHeader);
            boolean matchFound = paginationHeaderEntryMatcher.find();
            while (matchFound) {
                var uri = paginationHeaderEntryMatcher.group("uri");
                var rel = paginationHeaderEntryMatcher.group("rel");
                switch (rel) {
                    case "first":
                        first = URI.create(uri);
                        break;
                    case "prev":
                        previous = URI.create(uri);
                        break;
                    case "next":
                        next = URI.create(uri);
                        break;
                    case "last":
                        last = URI.create(uri);
                        break;
                }
                matchFound = paginationHeaderEntryMatcher.find();
            }
        }
        return new PaginationLinks(first, previous, next, last);
    }
}
