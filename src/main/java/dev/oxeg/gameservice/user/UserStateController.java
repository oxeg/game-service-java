package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.HttpUserState;
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

@Path("/user/{id}/state")
public class UserStateController {
    @Inject
    UserService service;

    @Operation(summary = "Get state for user")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User state",
                    content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = HttpUserState.class))}
            ),
            @APIResponse(responseCode = "400", description = "User id has invalid format"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HttpUserState getState(@PathParam("id") String id) {
        var userId = validateUserId(id);
        return service.getState(userId);
    }

    @Operation(summary = "Update state for user")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User state updated"),
            @APIResponse(responseCode = "400", description = "User id has invalid format"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateState(@PathParam("id") String id, HttpUserState userState) {
        var userId = validateUserId(id);
        service.saveState(userId, userState);
        return Response.ok().build();
    }
}
