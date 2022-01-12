package dev.oxeg.gameservice.user;

public class CreateUserRequest {
    public String name = "";

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name) {
        this.name = name;
    }
}
