package dev.oxeg.gameservice;

import org.junit.jupiter.api.Test;

import javax.ws.rs.BadRequestException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {
    @Test
    void validateUserId_throws_whenIdIsNull() {
        assertThrows(BadRequestException.class, () -> Utils.validateUserId(null));
    }

    @Test
    void validateUserId_throws_whenIdIsEmpty() {
        assertThrows(BadRequestException.class, () -> Utils.validateUserId(""));
    }

    @Test
    void validateUserId_throws_whenIdIsNotUUID() {
        assertThrows(BadRequestException.class, () -> Utils.validateUserId("random id format"));
    }

    @Test
    void validateUserId_returnsUUID_whenIdIsValid() {
        var id = UUID.randomUUID();

        var validatedId = Utils.validateUserId(id.toString());

        assertEquals(id, validatedId);
    }
}
