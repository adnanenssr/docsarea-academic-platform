package com.docsarea.dtos.user;

public class PasswordDto {

    String password = "password" ;
    String value ;
    boolean temporary = false ;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
