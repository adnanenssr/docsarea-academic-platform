package com.docsarea.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_tb")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    @Column(unique = true)
    String username ;
    @Column(unique = true)
    String email ;
    String password ;
    LocalDateTime joinedAt ;
    LocalDateTime updatedAt ;
    String profilePicUrl ;
    String firstname ;
    String lastname ;
    String bio ;
    String address ;
    String cover ;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    @PrePersist
    public void setJoinedAt() {
        this.joinedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now() ;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
