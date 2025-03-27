package com.ng.authen.rbac_app.model.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponse<T> {

    private String requestId;
    private boolean success;
    private T data;
    private List<String> errors;
}