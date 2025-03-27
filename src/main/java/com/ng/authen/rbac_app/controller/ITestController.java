package com.ng.authen.rbac_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ng.authen.rbac_app.constant.RbacAppConstant.*;

@RequestMapping(URI)
public interface ITestController {
    @GetMapping(ADMIN)
    ResponseEntity<String> adminEndpoint();

    @GetMapping(USER)
    ResponseEntity<String> userEndpoint();
}
