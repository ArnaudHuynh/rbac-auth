package com.ng.authen.rbac_app.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GetSidebarRequest extends BaseRequest {
}
