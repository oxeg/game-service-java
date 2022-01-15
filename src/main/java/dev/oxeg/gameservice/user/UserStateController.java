package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.HttpUserState;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static dev.oxeg.gameservice.Utils.validateUserId;

@Path("/user/{id}/state")
public class UserStateController {
    @Inject
    UserService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HttpUserState getState(@PathParam("id") String id) {
        var userId = validateUserId(id);
        return service.getState(userId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveState(@PathParam("id") String id, HttpUserState userState) {
        var userId = validateUserId(id);
        service.saveState(userId, userState);
        return Response.ok().build();
    }
}
