import database.GameStoreService;
import models.Game;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import resources.GameStoreResource;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ResourceTest {

    private GameStoreService service;
    private GameStoreResource resource;
    private JerseyTest test;

    @Before
    public void beforeAll() throws Exception {

        service = mock(GameStoreService.class);
        resource = new GameStoreResource(service);

        test = new JerseyTest() {
            @Override
            protected Application configure() {
                forceSet(TestProperties.CONTAINER_PORT, "0");
                return new ResourceConfig().register(resource);
            }
        };
        test.setUp();
    }

    @Test
    public void testGetGame(){
        Game game = new Game(1, "League", "Moba", 30, 0, 2008);
        when(service.getGame(1)).thenReturn(game);

        Game response = test
                .target("/games")
                .queryParam("id", 1)
                .request().get(Game.class);

        assertEquals(game.getName(), response.getName());
        verify(service).getGame(1);
    }

    @Test
    public void testGetGames() throws IOException {
        List<Game> games = Arrays.asList(
                new Game(1, "League", "Moba", 30, 0, 2008),
                new Game(2, "League1", "Moba1", 30, 0, 2008),
                new Game(3, "League2", "Moba2", 30, 0, 2008),
                new Game(4, "League3", "Moba3", 30, 0, 2008)
        );
        when(service.getGames()).thenReturn(games);

        ObjectMapper mapper = new ObjectMapper();


        String response =  test
                .target("/games")
                .request(MediaType.APPLICATION_JSON).get(String.class);

        List<Game> gameResponse = Arrays.asList(mapper.readValue(response, Game[].class));

        assertEquals(gameResponse.size(), games.size());
        assertEquals(gameResponse.get(1).getName(), games.get(1).getName());
        verify(service).getGames();
    }



    @Test
    public void testDeleteGames() {

        Response response = test
                .target("/games")
                .queryParam("id", 3)
                .request().delete();
        assertEquals("Should return status 400, game doest exist",
                400, response.getStatus());
    }

    @Test
    public void testGetRecord(){
        Response response = test
                .target("/records")
                .queryParam("id", 1)
                .request().get();

        assertEquals("Should return status 400, record doest exist",
                response.getStatus(), 400);
    }

    @Test
    public void testGetRecords(){
        Response response = test
                .target("/records")
                .request().get();

        assertEquals("Should return status 200, return empty list of records",
                200, response.getStatus());
    }

}
