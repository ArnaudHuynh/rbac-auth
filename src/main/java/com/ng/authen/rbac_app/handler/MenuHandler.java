package com.ng.authen.rbac_app.handler;


import com.ng.authen.rbac_app.exception.UnauthorizedException;
import com.ng.authen.rbac_app.model.SidebarItem;
import com.ng.authen.rbac_app.model.request.GetSidebarRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuHandler implements HandlerWrapper<GetSidebarRequest, BaseResponse<List<SidebarItem>>> {

    @Override
    public BaseResponse<List<SidebarItem>> handle(GetSidebarRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User must be authenticated to access menu");
        }

        List<SidebarItem> menuItems = new ArrayList<>();
        menuItems.add(new SidebarItem("Dashboard", "/dashboard", "fa-tachometer-alt"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

        if (isAdmin) {
            menuItems.add(new SidebarItem("User Management", "/user-management", "fa-users"));
            menuItems.add(new SidebarItem("Settings", "/settings", "fa-cog"));
        }

        if (isUser && !isAdmin) {
            menuItems.add(new SidebarItem("Profile", "/profile", "fa-user"));
        }

        return BaseResponse.<List<SidebarItem>>builder()
                .requestId(MDC.get("requestId"))
                .success(true)
                .data(menuItems)
                .build();
    }
}