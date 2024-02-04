package com.fypp.Ethics_Management_System.UserResLogin;

public class UserDTO {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public UserDTO(String username, String email, String accountType) {
        this.username = username;
        this.email = email;
        this.accountType = accountType;
    }

    private String username;
    private String email;
    private String accountType;
}
