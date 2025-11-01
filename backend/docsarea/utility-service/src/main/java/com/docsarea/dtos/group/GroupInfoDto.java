package com.docsarea.dtos.group;

import com.docsarea.enums.GroupPrivacy;
import com.docsarea.enums.JoinPolicy;
import com.docsarea.enums.OnMemberRemovalStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GroupInfoDto {
    String id ;
    @NotBlank
    String name;
    @NotBlank
    String theme ;
    @NotBlank
    String description ;

    @NotNull
    GroupPrivacy privacy ;
    @NotNull
    JoinPolicy joinPolicy ;
    String profileImg ;
    String coverImg ;

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    private OnMemberRemovalStrategy strategy ;

    public OnMemberRemovalStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(OnMemberRemovalStrategy strategy) {
        this.strategy = strategy;
    }

    public @NotNull GroupPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(@NotNull GroupPrivacy privacy) {
        this.privacy = privacy;
    }

    public @NotNull JoinPolicy getJoinPolicy() {
        return joinPolicy;
    }

    public void setJoinPolicy(@NotNull JoinPolicy joinPolicy) {
        this.joinPolicy = joinPolicy;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
