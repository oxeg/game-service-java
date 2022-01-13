package dev.oxeg.gameservice.user;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

@ApplicationScoped
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    @Inject
    UserRepository repository;

    public UserResponse createUser(String name) {
        var entity = repository.create(name);
        LOG.infov("User {0} created with name {1}", entity.getId(), entity.getName());
        return entityToResponse(entity);
    }

    public UserListResponse getAllUsers() {
        return repository.getAll().stream()
                .map(UserService::entityToResponse)
                .collect(collectingAndThen(Collectors.toList(), UserListResponse::new));
    }

    private static UserResponse entityToResponse(UserEntity entity) {
        return new UserResponse(entity.getId().toString(), entity.getName());
    }
}
