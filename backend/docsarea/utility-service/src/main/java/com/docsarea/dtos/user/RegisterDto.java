package com.docsarea.dtos.user;

import com.docsarea.utility.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDto {
    @NotBlank(message = "username is required ")
    @Size(min = 3)
    String username ;

    @NotBlank(message = "email is required ")
    @Email(message = "email must be valid")
    String email ;

    @ValidPassword(message = "does not met requirements ")
    String password ;

    @NotBlank(message = "firstname is required")
    String firstname ;
    @NotBlank(message = "lastname is required")
    String lastname ;

    public @NotBlank(message = "firstname is required") String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotBlank(message = "firstname is required") String firstname) {
        this.firstname = firstname;
    }

    public @NotBlank(message = "lastname is required") String getLastname() {
        return lastname;
    }

    public void setLastname(@NotBlank(message = "lastname is required") String lastname) {
        this.lastname = lastname;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
