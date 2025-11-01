package com.docsarea.model;


import java.io.Serializable;
import java.util.Objects;

public class MemberPk implements Serializable {
    public String groupId ;
    public String username ;


    public MemberPk(String groupId, String username) {
        this.groupId = groupId;
        this.username = username;
    }

    public MemberPk() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberPk memberPk)) return false;
        return Objects.equals(groupId, memberPk.groupId) && Objects.equals(username, memberPk.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, username);
    }
}
