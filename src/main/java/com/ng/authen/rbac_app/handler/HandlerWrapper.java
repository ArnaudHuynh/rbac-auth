package com.ng.authen.rbac_app.handler;

import com.ng.authen.rbac_app.model.request.BaseRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;

@FunctionalInterface
public interface HandlerWrapper<T extends BaseRequest, R extends BaseResponse<?>> {
    BaseResponse<?> handle(T request);
}