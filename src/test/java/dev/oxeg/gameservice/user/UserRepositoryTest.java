package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.DatabaseResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

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
}
