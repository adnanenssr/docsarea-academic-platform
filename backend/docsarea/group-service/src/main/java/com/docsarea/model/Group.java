package com.docsarea.model;

import com.docsarea.enums.GroupPrivacy;
import com.docsarea.enums.JoinPolicy;
import com.docsarea.enums.OnMemberRemovalStrategy;
import com.docsarea.utility.GenerateUlid;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    String id ;
    String name ;
    String theme ;
    String Description ;
    String owner ;
    String profileImg ;
    String coverImg ;
    @Column(updatable = false)
    LocalDateTime createdAt ;
    LocalDateTime modifiedAt ;




    @Enumerated(EnumType.STRING)
    JoinPolicy joinPolicy ;
    @Enumerated(EnumType.STRING)
    GroupPrivacy privacy ;
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private OnMemberRemovalStrategy strategy ;

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();

    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @PreUpdate
    public void setModifiedAt() {
        this.modifiedAt = LocalDateTime.now();
    }

    public JoinPolicy getJoinPolicy() {
        return joinPolicy;
    }

    public void setJoinPolicy(JoinPolicy joinPolicy) {
        this.joinPolicy = joinPolicy;
    }

    public GroupPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(GroupPrivacy privacy) {
        this.privacy = privacy;
    }

    public OnMemberRemovalStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(OnMemberRemovalStrategy strategy) {
        this.strategy = strategy;
    }
}
