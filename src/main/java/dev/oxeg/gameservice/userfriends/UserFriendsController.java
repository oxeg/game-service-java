package dev.oxeg.gameservice.userfriends;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user/{id}/friends")
public class UserFriendsController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FriendListResponse getFriends(@PathParam("id") String id) {
        return new FriendListResponse();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFriends(@PathParam("id") String id, UpdateFriendsRequest request) {
        return Response.ok().build();
    }
}
