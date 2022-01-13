package dev.oxeg.gameservice.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

@Schema(title = "List of users")
public record UserListResponse(List<UserResponse> users) {
    public UserListResponse() {
        this(Collections.emptyList());
    }
}
