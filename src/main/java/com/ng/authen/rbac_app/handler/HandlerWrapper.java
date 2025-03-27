package com.ng.authen.rbac_app.handler;

import com.ng.authen.rbac_app.model.request.BaseRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;

/**
 * The interface Handler wrapper.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface HandlerWrapper<T extends BaseRequest, R extends BaseResponse<?>> {
    /**
     * Handle base response.
     *
     * @param request the request
     * @return the base response
     */
    BaseResponse<?> handle(T request);
}