package dev.oxeg.gameservice.userfriends;

public class FriendResponse {
    public String id;
    public String name;
    public int highscore;

    public FriendResponse() {
    }

    public FriendResponse(String id, String name, int highscore) {
        this.id = id;
        this.name = name;
        this.highscore = highscore;
    }
}
