package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.DatabaseResource;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTestResource(DatabaseResource.class)
class UserRepositoryTest {
    private static final int USER_1_GAMES = 10;
    private static final int USER_1_SCORE = 100;
    private static final int USER_2_GAMES = 20;
    private static final int USER_2_SCORE = 200;
    private static final int USER_3_GAMES = 30;
    private static final int USER_3_SCORE = 300;

    private UserEntity user1;
    private UserEntity user2;
    private UserEntity user3;

    @Inject
    UserRepository repository;

    @BeforeAll
    @Transactional
    void setupDatabase() {
        user1 = repository.create("user1");
        user2 = repository.create("user2");
        user3 = repository.create("user3");

        repository.updateGamesPlayedAndScore(user1.getId(), USER_1_GAMES, USER_1_SCORE);
        repository.updateGamesPlayedAndScore(user2.getId(), USER_2_GAMES, USER_2_SCORE);
        repository.updateGamesPlayedAndScore(user3.getId(), USER_3_GAMES, USER_3_SCORE);

        List<UUID> user1Friends = List.of(user2.getId(), user3.getId());
        repository.updateFriendList(user1.getId(), user1Friends);
    }

    @Test
    @TestTransaction
    void create_createsNewUser() {
        var allUsers = repository.getAll();
        assertEquals(3, allUsers.size());

        repository.create("user4");
        allUsers = repository.getAll();
        assertEquals(4, allUsers.size());
    }

    @Test
    @TestTransaction
    void getById_returnsEmpty_whenUserDoesntExist() {
        var byId = repository.getById(UUID.randomUUID());

        assertTrue(byId.isEmpty());
    }

    @Test
    @TestTransaction
    void getById_returnsUserEntity_whenUserExists() {
        var byId = repository.getById(user1.getId());

        assertTrue(byId.isPresent());
        var userEntity = byId.get();
        assertEquals(userEntity.getGamesPlayed(), USER_1_GAMES);
        assertEquals(userEntity.getScore(), USER_1_SCORE);
    }

    @Test
    @TestTransaction
    void findFriendsScores_returnsNull_whenUserDoesntExist() {
        var friendsScores = repository.findFriendsScores(UUID.randomUUID());

        assertNull(friendsScores);
    }

    @Test
    @TestTransaction
    void findFriendsScores_returnsEmptyList_whenUserHasNoFriends() {
        var friendsScores = repository.findFriendsScores(user2.getId());

        assertNotNull(friendsScores);
        assertEquals(0, friendsScores.size());
    }

    @Test
    @TestTransaction
    void findFriendsScores_returnsFriendScores_whenUserHasFriends() {
        var friendsScores = repository.findFriendsScores(user1.getId());

        assertNotNull(friendsScores);
        assertEquals(2, friendsScores.size());

        var scoreMap = friendsScores.stream()
                .collect(Collectors.toMap(UserScoreData::id, Function.identity()));
        verifyUserScoreData(user2.getId(), user2.getName(), USER_2_SCORE, scoreMap);
        verifyUserScoreData(user3.getId(), user3.getName(), USER_3_SCORE, scoreMap);
    }

    private static void verifyUserScoreData(UUID friendId, String expectedName, int expectedScore, Map<UUID, UserScoreData> scoreMap) {
        var friendScore = scoreMap.get(friendId);
        assertNotNull(friendScore);
        assertEquals(expectedName, friendScore.name());
        assertEquals(expectedScore, friendScore.score());
    }

    @Test
    @TestTransaction
    void getAll_returnsAllUsers() {
        var allUsers = repository.getAll();

        assertEquals(3, allUsers.size());
    }

    @Test
    @TestTransaction
    void updateGamesPlayedAndScore_returnsFalse_whenUserDoesntExist() {
        var result = repository.updateGamesPlayedAndScore(UUID.randomUUID(), 1, 1);

        assertFalse(result);
    }

    @Test
    @TestTransaction
    void updateGamesPlayedAndScore_returnsTrueAndUpdates_whenUserExists() {
        var newGamesPlayed = USER_1_GAMES + 2;
        var newScore = USER_1_SCORE + 3;

        var result = repository.updateGamesPlayedAndScore(user1.getId(), newGamesPlayed, newScore);

        assertTrue(result);
        var newUser1ById = repository.getById(user1.getId());
        assertTrue(newUser1ById.isPresent());
        var newUser1 = newUser1ById.get();
        assertEquals(user1.getId(), newUser1.getId());
        assertEquals(newGamesPlayed, newUser1.getGamesPlayed());
        assertEquals(newScore, newUser1.getScore());
    }

    @Test
    @TestTransaction
    void updateFriendList_returnsFalse_whenUserDoesntExist() {
        var result = repository.updateFriendList(UUID.randomUUID(), Collections.emptyList());

        assertFalse(result);
    }

    @Test
    @TestTransaction
    void updateFriendList_returnsTrueAndRemovesFriends_whenUserExistsAndFriendListIsEmpty() {
        var result = repository.updateFriendList(user1.getId(), Collections.emptyList());

        assertTrue(result);
        var friendScores = repository.findFriendsScores(user1.getId());
        assertEquals(0, friendScores.size());
    }

    @Test
    @TestTransaction
    void updateFriendList_returnsTrueAndUpdatesFriends_whenUserExistsAndFriendListIsNotEmpty() {
        var result = repository.updateFriendList(user1.getId(), List.of(user2.getId()));

        assertTrue(result);
        var friendScores = repository.findFriendsScores(user1.getId());
        assertEquals(1, friendScores.size());
        assertEquals(user2.getId(), friendScores.get(0).id());
    }
}
