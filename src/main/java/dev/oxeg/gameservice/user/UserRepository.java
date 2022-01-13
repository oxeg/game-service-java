package dev.oxeg.gameservice.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

    public List<UserEntity> getAll() {
        return entityManager
                .createNamedQuery("user.getAll", UserEntity.class)
                .getResultList();
    }
}
