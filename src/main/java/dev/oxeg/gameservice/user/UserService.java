package dev.oxeg.gameservice.user;

import dev.oxeg.gameservice.user.httpdata.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

@ApplicationScoped
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    UserRepository repository;

    @Transactional
    public HttpUserResponse createUser(String name) {
        var entity = repository.create(name);
        LOG.infov("User {0} created with name {1}", entity.getId(), entity.getName());
        return entityToUserResponse(entity);
    }

    public HttpUserState getState(UUID userId) {
        return repository.getById(userId)
                .map(UserService::entityToUserStateResponse)
                .orElseThrow(UserService::userNotFound);
    }

    public HttpFriendListResponse getFriendsForUser(UUID userId) {
        var friendList = repository.findFriendsScores(userId);
        if (friendList == null) {
            throw userNotFound();
        }
        return friendList.stream()
                .map(UserService::scoreDataToFriendsResponse)
                .collect(collectingAndThen(Collectors.toList(), HttpFriendListResponse::new));
    }

    public HttpUserListResponse getAllUsers() {
        return repository.getAll().stream()
                .map(UserService::entityToUserResponse)
                .collect(collectingAndThen(Collectors.toList(), HttpUserListResponse::new));
    }

    @Transactional
    public void saveState(UUID userId, HttpUserState userState) {
        var userFound = repository.updateGamesPlayedAndScore(userId, userState.gamesPlayed(), userState.score());
        if (!userFound) {
            throw userNotFound();
        }
    }

    @Transactional
    public void saveFriends(UUID userId, List<UUID> friendIds) {
        var uniqueIds = friendIds.stream()
                .distinct()
                .toList();

        var userFound = repository.updateFriendList(userId, uniqueIds);
        if (!userFound) {
            throw userNotFound();
        }
        var joiner = new StringJoiner(", ", "[", "]");
        uniqueIds.forEach(id -> joiner.add(id.toString()));
        LOG.infov("Updated friends for user {0}: {1}", userId, joiner.toString());
    }

    static NotFoundException userNotFound() {
        return new NotFoundException("User not found");
    }

    private static HttpUserResponse entityToUserResponse(UserEntity entity) {
        return new HttpUserResponse(entity.getId(), entity.getName());
    }

    private static HttpFriendResponse scoreDataToFriendsResponse(UserScoreData scoreData) {
        return new HttpFriendResponse(scoreData.id(), scoreData.name(), scoreData.score());
    }

    private static HttpUserState entityToUserStateResponse(UserEntity entity) {
        return new HttpUserState(entity.getGamesPlayed(), entity.getScore());
    }
}
