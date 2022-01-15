package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.HttpFriendListResponse;
import dev.oxeg.gameservice.user.httpdata.HttpFriendResponse;
import dev.oxeg.gameservice.user.httpdata.HttpUpdateFriendsRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@QuarkusTest
class UserFriendsControllerTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID NO_FRIENDS_USER_ID = UUID.randomUUID();
    private static final HttpFriendResponse FRIEND_1 = new HttpFriendResponse(UUID.randomUUID(), "friend1", 1);
    private static final HttpFriendResponse FRIEND_2 = new HttpFriendResponse(UUID.randomUUID(), "friend2", 2);
    private static final HttpFriendListResponse USER_FRIENDS = new HttpFriendListResponse(List.of(FRIEND_1, FRIEND_2));
    private static final HttpFriendListResponse NO_FRIENDS = new HttpFriendListResponse();
    private static final HttpUpdateFriendsRequest UPDATE_FRIENDS_REQUEST = new HttpUpdateFriendsRequest(List.of(NO_FRIENDS_USER_ID));

    @InjectMock
    private UserService service;

    @BeforeEach
    void setupFriends() {
        doThrow(UserService.userNotFound()).when(service).getFriendsForUser(any());
        doReturn(USER_FRIENDS).when(service).getFriendsForUser(eq(USER_ID));
        doReturn(NO_FRIENDS).when(service).getFriendsForUser(eq(NO_FRIENDS_USER_ID));

        doThrow(UserService.userNotFound()).when(service).saveFriends(any(), any());
        doNothing().when(service).saveFriends(eq(USER_ID), any());
    }

    @Test
    void getFriends_returnsBadRequest_whenUserIdIsInvalid() {
        var response = given()
                .when()
                .get("/user/user_id/friends");

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        verify(service, never()).getFriendsForUser(any());
    }

    @Test
    void getFriends_returnsNotFound_whenUserDoesntExist() {
        var response = given()
                .when()
                .get("/user/" + UUID.randomUUID() + "/friends");

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    void getFriends_returnsNoFriends_whenUserHasNoFriends() {
        var response = given()
                .when()
                .get("/user/" + NO_FRIENDS_USER_ID + "/friends");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        final var friendListResponse = response.as(HttpFriendListResponse.class);
        assertEquals(0, friendListResponse.friends().size());
    }

    @Test
    void getFriends_returnsFriends_whenUserHasFriends() {
        var response = given()
                .when()
                .get("/user/" + USER_ID + "/friends");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        final var friendListResponse = response.as(HttpFriendListResponse.class);
        assertEquals(USER_FRIENDS.friends().size(), friendListResponse.friends().size());
    }

    @Test
    void updateFriends_returnsBadRequest_whenUserIdIsInvalid() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(UPDATE_FRIENDS_REQUEST)
                .when()
                .put("/user/user_id/friends");

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
        verify(service, never()).saveFriends(any(), any());
    }

    @Test
    void updateFriends_returnsNotFound_whenUserDoesntExist() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(UPDATE_FRIENDS_REQUEST)
                .when()
                .put("/user/" + UUID.randomUUID() + "/friends");

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    @Test
    void updateFriends_returnsOK_whenUserExists() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(UPDATE_FRIENDS_REQUEST)
                .when()
                .put("/user/" + USER_ID + "/friends");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }
}
