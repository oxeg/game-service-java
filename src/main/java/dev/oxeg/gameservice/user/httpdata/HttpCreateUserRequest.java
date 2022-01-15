package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Request for Creating user")
public record HttpCreateUserRequest(@Schema(required = true) String name) {
}
