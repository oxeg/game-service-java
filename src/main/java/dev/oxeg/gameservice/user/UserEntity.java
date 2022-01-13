package dev.oxeg.gameservice.user;

import javax.persistence.*;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(
                name = "user.getAll",
                query = "select u from UserEntity u"
        )
})
@Entity
@Table(name = "player")
public class UserEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    public UserEntity() {
    }

    public UserEntity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
