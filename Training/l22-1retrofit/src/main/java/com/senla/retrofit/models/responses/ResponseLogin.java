package com.senla.retrofit.models.responses;

public class ResponseLogin extends Response{
    private String token;

    public ResponseLogin(){}

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
