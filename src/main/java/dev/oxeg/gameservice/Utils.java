package dev.oxeg.gameservice;

import javax.ws.rs.BadRequestException;
import java.util.UUID;

public class Utils {
    public static UUID validateUserId(String id) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Empty user id");
        }
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException ignored) {
            throw new BadRequestException("Invalid user id format");
        }
    }
}
