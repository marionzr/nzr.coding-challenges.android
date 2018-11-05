package com.ssannttoss.ciandt.mvc.model.entity;

import com.ssannttoss.framework.mvc.model.Entity;

public class Login extends Entity<Login> {
    private String username;
    private String password;
    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Login withUsername(String username) {
        setUsername(username);
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Login withPassword(String password) {
        setPassword(password);
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Login withAuthority(String authority) {
        setAuthority(authority);
        return this;
    }

    @Override
    public Login clone() {
        return new Login();
    }
}
