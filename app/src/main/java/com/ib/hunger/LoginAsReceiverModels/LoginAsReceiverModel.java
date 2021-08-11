package com.ib.hunger.LoginAsReceiverModels;

public class LoginAsReceiverModel {
    String email;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginAsReceiverModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    String password;
}

