package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

@Schema(title = "List of users")
public record HttpUserListResponse(List<HttpUserResponse> users) {
    public HttpUserListResponse() {
        this(Collections.emptyList());
    }
}
