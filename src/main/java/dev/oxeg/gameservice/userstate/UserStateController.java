package dev.oxeg.gameservice.userstate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user/{id}/state")
public class UserStateController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserStateData getState(@PathParam("id") String id) {
        return new UserStateData(0, 0);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveState(@PathParam("id") String id, UserStateData data) {
        return Response.ok().build();
    }
}
