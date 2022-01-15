package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Schema(title = "Request to update friends list")
public record HttpUpdateFriendsRequest(@Schema(required = true) List<UUID> friends) {
    public HttpUpdateFriendsRequest() {
        this(Collections.emptyList());
    }
}
