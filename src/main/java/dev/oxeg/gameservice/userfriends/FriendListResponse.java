package dev.oxeg.gameservice.userfriends;

import java.util.Collections;
import java.util.List;

public record FriendListResponse(List<FriendResponse> friends) {
    public FriendListResponse() {
        this(Collections.emptyList());
    }
}
