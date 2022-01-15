package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

@Schema(title = "List of friends")
public record HttpFriendListResponse(List<HttpFriendResponse> friends) {
    public HttpFriendListResponse() {
        this(Collections.emptyList());
    }
}
