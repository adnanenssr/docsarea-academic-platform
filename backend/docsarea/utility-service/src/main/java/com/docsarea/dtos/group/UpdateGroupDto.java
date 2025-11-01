package com.docsarea.dtos.group;

import com.docsarea.enums.GroupPrivacy;
import com.docsarea.enums.JoinPolicy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateGroupDto {
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
