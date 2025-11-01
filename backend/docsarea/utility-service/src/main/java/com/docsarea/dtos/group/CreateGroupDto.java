package com.docsarea.dtos.group;

import com.docsarea.enums.GroupPrivacy;
import com.docsarea.enums.JoinPolicy;
import com.docsarea.enums.OnMemberRemovalStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

public class CreateGroupDto {
    @NotBlank
    String description ;
    @NotBlank
    String name ;
    @NotBlank
    String theme ;
    @NotNull
    GroupPrivacy privacy ;
    @NotNull
    JoinPolicy joinPolicy ;
    @NotNull
    OnMemberRemovalStrategy strategy ;

    boolean enableFileReview ;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public GroupPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(GroupPrivacy privacy) {
        this.privacy = privacy;
    }

    public JoinPolicy getJoinPolicy() {
        return joinPolicy;
    }

    public void setJoinPolicy(JoinPolicy joinPolicy) {
        this.joinPolicy = joinPolicy;
    }

    public OnMemberRemovalStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(OnMemberRemovalStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isEnableFileReview() {
        return enableFileReview;
    }

    public void setEnableFileReview(boolean enableFileReview) {
        this.enableFileReview = enableFileReview;
    }
}
