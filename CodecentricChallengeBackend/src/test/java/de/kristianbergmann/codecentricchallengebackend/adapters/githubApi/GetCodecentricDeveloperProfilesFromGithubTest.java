package de.kristianbergmann.codecentricchallengebackend.adapters.githubApi;

import de.kristianbergmann.codecentricchallengebackend.application.datamodel.ProgrammingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCodecentricDeveloperProfilesFromGithubTest {
    private GetCodecentricDeveloperProfilesFromGithub tested;

    @BeforeEach
    public void setup() {
        tested = new GetCodecentricDeveloperProfilesFromGithub(2);
    }

    @Test
    public void queriesOrganizationMembersFromGithub() {
        var developers = tested.getOrganizationMembers("https://api.github.com/orgs/codecentric/members");
        assertThat(developers.length).withFailMessage("Probably only first page was retrieved").isGreaterThan(30);
        assertThat(developers).anyMatch(d -> d.login.equals("danielbayerlein") && d.repos_url.equals("https://api.github.com/users/danielbayerlein/repos"));
        assertThat(developers).anyMatch(d -> d.login.equals("ufried"));
    }

    @Test
    public void parsesPaginationHeadersWithNextPage() throws URISyntaxException {
        var parsedHeaders = tested.parsePaginationHeader("<https://api.github.com/organizations/1009716/members?page=2>; rel=\"next\", <https://api.github.com/organizations/1009716/members?page=2>; rel=\"last\"");
        assertThat(parsedHeaders.first()).isNull();
        assertThat(parsedHeaders.previous()).isNull();
        assertThat(parsedHeaders.next()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=2"));
        assertThat(parsedHeaders.last()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=2"));
    }

    @Test
    public void parsesPaginationHeadersWithPreviousPage() throws URISyntaxException {
        var parsedHeaders = tested.parsePaginationHeader("<https://api.github.com/organizations/1009716/members?page=1>; rel=\"prev\", <https://api.github.com/organizations/1009716/members?page=1>; rel=\"first\"");
        assertThat(parsedHeaders.first()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=1"));
        assertThat(parsedHeaders.previous()).isEqualTo(new URI("https://api.github.com/organizations/1009716/members?page=1"));
        assertThat(parsedHeaders.next()).isNull();
        assertThat(parsedHeaders.last()).isNull();
    }

    @Test
    public void returnsEmptyPaginationHeaderForAbsentHeader() {
        var parsedHeaders = tested.parsePaginationHeader(null);
        assertThat(parsedHeaders).isEqualTo(new PaginationLinks(null, null, null, null));
    }

    @Test
    public void queriesUserReposFromGithub() {
        var repositories = tested.getRepositories("https://api.github.com/users/denniseffing/repos");
        assertThat(repositories.length).withFailMessage("Probably only first page was retrieved").isGreaterThan(30);
        assertThat(repositories).anyMatch(r -> r.name.equals("istio-chaos-demo") && r.languages_url.equals("https://api.github.com/repos/denniseffing/istio-chaos-demo/languages"));
        assertThat(repositories).anyMatch(r -> r.name.equals("titanium-server"));
    }

    @Test
    public void queriesRepoLanguagesFromGithub() {
        var languages = tested.getLanguages("https://api.github.com/repos/denniseffing/istio-chaos-demo/languages");
        assertThat(languages.size()).isEqualTo(2);
        assertThat(languages.stream().map(ProgrammingLanguage::name)).contains("Java", "Dockerfile");
    }

    @Test
    public void compilesDatamodelForUsecaseUsingGithubQueriesInternally() {
        //note that child requests are limited to 2 in setup

        var developers = tested.getDevelopers();

        assertThat(developers.size()).isEqualTo(2);

        var dev = developers.stream().filter(d -> d.name().equals("danielbayerlein")).findFirst().get();
        assertThat(dev.repositories().size()).isEqualTo(2);

        var repo = dev.repositories().stream().filter(r -> r.name().equals("amplify-js")).findFirst().get();
        assertThat(repo.languages().size()).isEqualTo(10);
        assertThat(repo.languages().stream().map(ProgrammingLanguage::name)).contains("TypeScript", "HTML");
    }

}
