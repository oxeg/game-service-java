package dev.oxeg.gameservice.user;

import java.util.Collections;
import java.util.List;

public class UserListResponse {
    public List<UserResponse> users = Collections.emptyList();

    public UserListResponse() {
    }

    public UserListResponse(List<UserResponse> users) {
        this.users = users;
    }
}
