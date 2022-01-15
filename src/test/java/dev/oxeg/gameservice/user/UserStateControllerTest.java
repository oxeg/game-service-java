package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.HttpUserState;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@QuarkusTest
class UserStateControllerTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final HttpUserState USER_STATE = new HttpUserState(10, 100);

    @InjectMock
    private UserService service;

    @BeforeEach
    void setupState() {
        doThrow(UserService.userNotFound()).when(service).getState(any());
        doReturn(USER_STATE).when(service).getState(eq(USER_ID));

        doThrow(UserService.userNotFound()).when(service).saveState(any(), any());
        doNothing().when(service).saveState(eq(USER_ID), any());
    }

    @Test
    void getState_returnsBadRequest_whenUserIdIsInvalid() {
        var response = given()
                .when()
                .get("/user/user_id/state");

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        verify(service, never()).getState(any());
    }

    @Test
    void getState_returnsNotFound_whenUserDoesntExist() {
        var response = given()
                .when()
                .get("/user/" + UUID.randomUUID() + "/state");

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    void getState_returnsState_whenUserExists() {
        var response = given()
                .when()
                .get("/user/" + USER_ID + "/state");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        final var stateResponse = response.as(HttpUserState.class);
        assertEquals(USER_STATE.gamesPlayed(), stateResponse.gamesPlayed());
        assertEquals(USER_STATE.score(), stateResponse.score());
    }

    @Test
    void updateState_returnsBadRequest_whenUserIdIsInvalid() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(USER_STATE)
                .when()
                .put("/user/user_id/state");

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        verify(service, never()).saveState(any(), any());
    }

    @Test
    void updateState_returnsNotFound_whenUserDoesntExist() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(USER_STATE)
                .when()
                .put("/user/" + UUID.randomUUID() + "/state");

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    void updateState_returnsOk_whenUserExists() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(USER_STATE)
                .when()
                .put("/user/" + USER_ID + "/state");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }
}
