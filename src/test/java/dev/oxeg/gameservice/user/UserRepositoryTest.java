package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.DatabaseResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
class UserRepositoryTest {
    @Inject
    UserRepository repository;

    @Test
    void createUser_createsNewUser() {
        var allUsers = repository.getAll();
        assertEquals(0, allUsers.size());

        repository.create("user1");
        allUsers = repository.getAll();
        assertEquals(1, allUsers.size());

        repository.create("user2");
        allUsers = repository.getAll();
        assertEquals(2, allUsers.size());
    }

    @Test
    void testGetScoreData() {
        var user1 = repository.create("user1");
        var user2 = repository.create("user2");
        var user3 = repository.create("user3");
        var user4 = repository.create("user4");

        repository.updateGamesPlayedAndScore(user2.getId(), 4, 444);
        repository.updateGamesPlayedAndScore(user3.getId(), 6, 666);
        repository.updateGamesPlayedAndScore(user4.getId(), 8, 888);

        repository.updateFriendList(user1.getId(), List.of(user2.getId(), user3.getId(), user4.getId()));

        var scoresByUserIds = repository.findFriendsScores(user1.getId());
        assertEquals(3, scoresByUserIds.size());
    }
}
