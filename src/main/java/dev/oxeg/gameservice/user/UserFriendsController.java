package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.HttpFriendListResponse;
import dev.oxeg.gameservice.user.httpdata.HttpUpdateFriendsRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static dev.oxeg.gameservice.Utils.validateUserId;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user/{id}/friends")
public class UserFriendsController {
    @Inject
    UserService service;

    @Operation(summary = "Get friend list for user")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "List of friend data",
                    content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = HttpFriendListResponse.class))}
            ),
            @APIResponse(responseCode = "400", description = "User id has invalid format"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HttpFriendListResponse getFriends(@PathParam("id") String id) {
        var userId = validateUserId(id);
        return service.getFriendsForUser(userId);
    }

    @Operation(summary = "Update friend list for user")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Friend list updated"),
            @APIResponse(responseCode = "400", description = "User id has invalid format"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFriends(@PathParam("id") String id, HttpUpdateFriendsRequest request) {
        var userId = validateUserId(id);
        service.saveFriends(userId, request.friends());
        return Response.ok().build();
    }
}
