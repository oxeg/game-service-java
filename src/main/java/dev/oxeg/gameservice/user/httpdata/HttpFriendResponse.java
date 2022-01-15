package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Schema(title = "Friend data")
public record HttpFriendResponse(UUID id, String name, int highscore) {
}
