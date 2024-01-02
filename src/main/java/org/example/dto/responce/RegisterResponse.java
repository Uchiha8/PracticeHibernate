package org.example.dto.responce;

public class RegisterResponse {
    private String username;
    private String password;

    public RegisterResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegisterResponse() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
