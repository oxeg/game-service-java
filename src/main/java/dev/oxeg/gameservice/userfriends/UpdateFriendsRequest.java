package dev.oxeg.gameservice.userfriends;

import java.util.Collections;
import java.util.List;

public record UpdateFriendsRequest(List<String> friends) {
    public UpdateFriendsRequest() {
        this(Collections.emptyList());
    }
}
