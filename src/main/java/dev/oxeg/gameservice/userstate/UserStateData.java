package dev.oxeg.gameservice.userstate;

public class UserStateData {
    public int gamesPlayed;
    public int score;

    public UserStateData() {
    }

    public UserStateData(int gamesPlayed, int score) {
        this.gamesPlayed = gamesPlayed;
        this.score = score;
    }
}
