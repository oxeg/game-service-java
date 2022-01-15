package dev.oxeg.gameservice.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Transactional
public class UserRepository {
    @Inject
    EntityManager entityManager;

    public UserEntity create(String name) {
        var entity = new UserEntity(name);
        entityManager.persist(entity);
        return entity;
    }

    Optional<UserEntity> getById(UUID userId) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, userId));
    }

    List<UserScoreData> findFriendsScores(UUID userId) {
        var query = entityManager.createNativeQuery("""
                SELECT u.id, u.name, u.score
                FROM user_friend f
                INNER JOIN user_list u ON f.user_to = u.id
                WHERE f.user_from = :userId
                """, "UserScoreData");
        query.setParameter("userId", userId);
        //noinspection unchecked
        return (List<UserScoreData>) query.getResultList();
    }

    List<UserEntity> getAll() {
        var query = entityManager.createQuery("""
                SELECT u.
                FROM UserEntity u
                """, UserEntity.class);
        return query.getResultList();
    }

    boolean updateGamesPlayedAndScore(UUID userId, int gamesPlayed, int score) {
        var user = entityManager.find(UserEntity.class, userId);
        if (user == null) {
            return false;
        }
        user.setGamesPlayed(gamesPlayed);
        user.setScore(score);
        return true;
    }

    boolean updateFriendList(UUID userId, List<UUID> friendIds) {
        var user = entityManager.find(UserEntity.class, userId);
        if (user == null) {
            return false;
        }

        var deleteExistingFriends = entityManager.createNativeQuery("""
                DELETE
                FROM user_friend f
                WHERE f.user_from = :userId
                """);
        deleteExistingFriends.setParameter("userId", userId);
        deleteExistingFriends.executeUpdate();

        friendIds.forEach(id -> entityManager.persist(new UserFriendEntity(userId, id)));
        return true;
    }
}
