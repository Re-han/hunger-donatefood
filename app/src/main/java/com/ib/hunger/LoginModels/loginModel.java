package com.ib.hunger.LoginModels;

public class loginModel {
    String email;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public loginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    String password;
}
