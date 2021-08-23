package com.auth.server.dto.response;

public class CommonRes {

    private final Object body;

    public CommonRes(Object body) {
        this.body = body;
    }

    public Object getBody() {
        return body;
    }
}
