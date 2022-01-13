package dev.oxeg.gameservice.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Request for Creating user")
public record CreateUserRequest(@Schema(required = true)String name) {
}
