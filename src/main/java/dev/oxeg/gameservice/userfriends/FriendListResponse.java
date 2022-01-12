package dev.oxeg.gameservice.userfriends;

import java.util.Collections;
import java.util.List;

public class FriendListResponse {
    public List<FriendResponse> friends = Collections.emptyList();

    public FriendListResponse() {
    }

    public FriendListResponse(List<FriendResponse> friends) {
        this.friends = friends;
    }
}
