package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.HttpCreateUserRequest;
import dev.oxeg.gameservice.user.httpdata.HttpUserListResponse;
import dev.oxeg.gameservice.user.httpdata.HttpUserResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@QuarkusTest
class UserControllerTest {
    private static final HttpUserResponse USER_JOHN = new HttpUserResponse(UUID.randomUUID(), "John");
    private static final HttpUserResponse USER_SARAH = new HttpUserResponse(UUID.randomUUID(), "Sarah");
    private static final HttpUserListResponse ALL_USERS = new HttpUserListResponse(List.of(USER_JOHN, USER_SARAH));

    @InjectMock
    private UserService service;

    @BeforeEach
    void setupService() {
        when(service.createUser(anyString())).thenReturn(USER_JOHN);
        when(service.getAllUsers()).thenReturn(ALL_USERS);
    }

    @Test
    void createUser_returnsBadRequest_whenNameIsNull() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new HttpCreateUserRequest(null))
                .when()
                .post("/user");

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        verify(service, never()).createUser(anyString());
    }

    @Test
    void createUser_returnsBadRequest_whenNameIsEmpty() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new HttpCreateUserRequest(""))
                .when()
                .post("/user");

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        verify(service, never()).createUser(anyString());
    }

    @Test
    void createUser_returnsNewUser_whenNameSet() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new HttpCreateUserRequest(USER_JOHN.name()))
                .when()
                .post("/user");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        verify(service, times(1)).createUser(anyString());
        final var userResponse = response.as(HttpUserResponse.class);
        assertEquals(USER_JOHN.name(), userResponse.name());
        assertEquals(USER_JOHN.id(), userResponse.id());
    }

    @Test
    void getAll_returnsListOfUsers() {
        var response = given()
                .when()
                .get("/user");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertDoesNotThrow(() -> response.as(HttpUserListResponse.class));
    }
}
