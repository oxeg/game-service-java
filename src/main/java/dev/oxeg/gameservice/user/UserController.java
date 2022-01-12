package dev.oxeg.gameservice.user;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/user")
public class UserController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserListResponse getAll() {
        return new UserListResponse();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse createUser(CreateUserRequest request) {
        return new UserResponse(UUID.randomUUID().toString(), request.name);
    }
}
