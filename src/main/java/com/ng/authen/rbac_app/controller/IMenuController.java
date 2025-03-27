package com.ng.authen.rbac_app.controller;

import com.ng.authen.rbac_app.model.SidebarItem;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ng.authen.rbac_app.constant.RbacAppConstant.MENU;
import static com.ng.authen.rbac_app.constant.RbacAppConstant.SIDEBAR;

@RequestMapping(MENU)
public interface IMenuController {

    @GetMapping(SIDEBAR)
    ResponseEntity<BaseResponse<List<SidebarItem>>> getSideBar();
}
