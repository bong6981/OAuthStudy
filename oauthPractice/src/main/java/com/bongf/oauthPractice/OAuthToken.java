package com.bongf.oauthPractice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class OAuthToken {
    private String accesToken;
    private String tokenType;
    private String scope;
    private String bearer;

    @JsonProperty("access_token")
    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }

    public String getBearer() {
        return bearer;
    }
}
