package com.ng.authen.rbac_app.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}