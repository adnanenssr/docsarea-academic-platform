package com.docsarea.dtos.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenDto {

    @JsonProperty("access_token")
    String accessToken ;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
