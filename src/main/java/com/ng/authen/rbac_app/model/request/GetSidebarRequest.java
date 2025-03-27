package com.ng.authen.rbac_app.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * The type Get sidebar request.
 */
@SuperBuilder(toBuilder = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GetSidebarRequest extends BaseRequest {
}
