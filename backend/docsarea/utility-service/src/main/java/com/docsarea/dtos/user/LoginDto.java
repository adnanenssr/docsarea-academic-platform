package com.docsarea.dtos.user;

import jakarta.validation.constraints.NotEmpty;

public class LoginDto {

    @NotEmpty
    String username ;
    @NotEmpty
    String password ;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }
}
