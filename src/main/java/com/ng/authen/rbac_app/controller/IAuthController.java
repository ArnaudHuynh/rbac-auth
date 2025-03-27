package com.ng.authen.rbac_app.controller;

import com.ng.authen.rbac_app.model.request.LoginRequest;
import com.ng.authen.rbac_app.model.request.RegisterRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import com.ng.authen.rbac_app.model.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ng.authen.rbac_app.constant.RbacAppConstant.*;

@RequestMapping(AUTH)
public interface IAuthController {

    @PostMapping(REGISTER)
    ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterRequest request);

    @PostMapping(LOGIN)
    ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request);

    @PostMapping(LOGOUT)
    ResponseEntity<?> logout(HttpServletRequest request);
}
