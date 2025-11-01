package com.docsarea.enums;

public enum GroupRoles {
    OWNER(1),
    ADMIN(2),
    MODERATOR(3),
    MEMBER(4);

    private final int grade ;
    GroupRoles(int grade){
        this.grade = grade ;
    }

    public boolean isHigherThan(GroupRoles role){
        return this.grade < role.grade ;
    }
}
