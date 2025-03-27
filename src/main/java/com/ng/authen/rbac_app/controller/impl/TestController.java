package com.ng.authen.rbac_app.controller.impl;

import com.ng.authen.rbac_app.controller.ITestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements ITestController {

    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Hello, Admin!");
    }

    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("Hello, User!");
    }
}