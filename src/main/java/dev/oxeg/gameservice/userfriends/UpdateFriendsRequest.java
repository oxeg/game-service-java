package dev.oxeg.gameservice.userfriends;

import java.util.Collections;
import java.util.List;

public class UpdateFriendsRequest {
    public List<String> friends = Collections.emptyList();

    public UpdateFriendsRequest() {
    }

    public UpdateFriendsRequest(List<String> friends) {
        this.friends = friends;
    }
}
