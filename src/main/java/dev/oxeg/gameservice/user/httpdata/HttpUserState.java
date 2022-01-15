package dev.oxeg.gameservice.user.httpdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "User state data")
public record HttpUserState(@Schema(required = true) int gamesPlayed, @Schema(required = true) int score) {
}
