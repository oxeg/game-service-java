package dev.oxeg.gameservice.user;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
public class UserController {
    @Inject
    UserService service;

    @Operation(summary = "Create user")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "User successfully created. Response content new user id and name",
                    content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserResponse.class))}
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "User name is null or empty"
            )
    })
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public UserResponse createUser(CreateUserRequest request) {
        if (request.name() == null || request.name().isEmpty()) {
            throw new BadRequestException("name is not set");
        }
        return service.createUser(request.name());
    }

    @Operation(summary = "Get all users")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "List of all users",
                    content = {@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserListResponse.class))}
            )
    })
    @GET
    @Produces(APPLICATION_JSON)
    public UserListResponse getAll() {
        return service.getAllUsers();
    }
}
