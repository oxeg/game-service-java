package dev.oxeg.gameservice.user;

import java.util.UUID;

public record UserScoreData(UUID id, String name, int score) {
}
