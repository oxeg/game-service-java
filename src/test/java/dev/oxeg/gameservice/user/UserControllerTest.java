package dev.oxeg.gameservice.user;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class UserControllerTest {
    @Test
    public void testGetAllUsers() {
        var response = given()
                .when()
                .get("/user");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void testCreateUser() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new CreateUserRequest("John"))
                .when()
                .post("/user");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        final var userResponse = response.as(UserResponse.class);
        assertEquals("John", userResponse.name);
        assertDoesNotThrow(() -> UUID.fromString(userResponse.id));
    }
}
