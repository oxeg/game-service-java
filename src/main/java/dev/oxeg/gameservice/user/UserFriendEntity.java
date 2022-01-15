package dev.oxeg.gameservice.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@SqlResultSetMapping(
        name = "UserScoreData",
        classes = {
                @ConstructorResult(targetClass = UserScoreData.class, columns = {@ColumnResult(name = "id", type = UUID.class), @ColumnResult(name = "name"), @ColumnResult(name = "score")})
        }
)
@Entity
@Table(name = "user_friend")
public class UserFriendEntity implements Serializable {
    @Id
    @Column(name = "user_from", columnDefinition = "uuid-char")
    private UUID userFrom;

    @Id
    @Column(name = "user_to", columnDefinition = "uuid-char")
    private UUID userTo;

    public UserFriendEntity() {
    }

    public UserFriendEntity(UUID userFrom, UUID userTo) {
        this.userFrom = userFrom;
        this.userTo = userTo;
    }

    public UUID getUserFrom() {
        return userFrom;
    }

    public UUID getUserTo() {
        return userTo;
    }
}
