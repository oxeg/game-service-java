package dev.oxeg.gameservice.user;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@DynamicUpdate
@Table(name = "user_list")
public class UserEntity {
    @Id
    @Column(name = "id", columnDefinition = "uuid-char")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "games_played")
    private int gamesPlayed;

    @Column(name = "score")
    private int score;

    public UserEntity() {
    }

    public UserEntity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.gamesPlayed = 0;
        this.score = 0;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
