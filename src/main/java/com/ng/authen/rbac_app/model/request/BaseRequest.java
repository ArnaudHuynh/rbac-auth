package com.ng.authen.rbac_app.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseRequest {
    private Long userId; // RBAC user ID (optional, populated after authentication)
    private Set<String> roles; // RBAC roles (optional, populated after authentication)
}