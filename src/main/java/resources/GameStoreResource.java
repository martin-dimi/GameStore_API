package resources;


import com.google.inject.Inject;
import database.GameStoreService;
import models.Game;
import models.Record;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Path("/")
public class GameStoreResource {

    private final GameStoreService service;
    private final String localURL = "http://localhost:8080";

    @Inject
    public GameStoreResource(GameStoreService service) {
        this.service = service;
    }


    @GET
    @Path("/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames(@QueryParam("id") int id){
        if (id == 0)
            return Response.ok(service.getGames()).build();
        else {
            Game game = service.getGame(id);
            return (game != null) ?
                    Response.ok(game).build() :
                    Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/records")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecord(@QueryParam("id") int id) {
        if (id == 0)
            return Response.ok(service.getRecords()).build();
        else {
            Record record = service.getRecord(id);
            return (record != null) ?
                 Response.ok(record).build() :
                 Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/games")
    public Response createGame(Game game) throws URISyntaxException {
        Game temp = service.getGame(game.getId());
        if(temp == null) {
            service.insertGame(game);
            return Response.seeOther(new URI(localURL +"/games")).build();
        }
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/records")
    public Response createRecord(Record record) throws URISyntaxException {
        Record temp = service.getRecord(record.getId());
        if(temp == null) {
            service.insertRecord(record);
            return Response.seeOther(new URI(localURL + "/records")).build();
        }
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/games")
    public Response deleteGame(@QueryParam("id") int id) {
        try {
            Game temp = service.getGame(id);
            if(temp != null) {
                service.deleteGame(id);
                return Response.seeOther(new URI(localURL + "/games")).build();
            }
            else
                return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/records")
    public Response deleteRecord(@QueryParam("id") int id) {
        try {
            Record temp = service.getRecord(id);
            if(temp != null) {
                service.deleteRecord(id);
                return Response.seeOther(new URI(localURL + "/records")).build();
            }
            else
                return Response.status(Response.Status.NOT_FOUND).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @GET
    @Path("/records/sales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSales(@QueryParam("from") int from, @QueryParam("to") int to){
        String sales = service.getSales(from, to);
        if(sales == null || sales.equals(""))
            return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.ok(sales).build();
    }

    @PUT
    @Path("games/sale")
    public Response informSale(@QueryParam("id") int id, @QueryParam("price") int price) throws MalformedURLException, URISyntaxException {

        Game game = service.getGame(id);
        if(game == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        else{
            if(game.getStock() > 0){
                service.saleGame(id, price);
                return Response.seeOther(new URL(localURL + "/games").toURI()).build();
            }
            return Response.ok("There is no stock left").build();
        }

    }

}
