package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Schema(title = "Newly created user")
public record HttpUserResponse(UUID id, String name) {
}
