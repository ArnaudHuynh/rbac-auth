package com.ng.authen.rbac_app.controller.impl;

import com.ng.authen.rbac_app.controller.IMenuController;
import com.ng.authen.rbac_app.handler.MenuHandler;
import com.ng.authen.rbac_app.model.SidebarItem;
import com.ng.authen.rbac_app.model.request.GetSidebarRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Menu controller.
 */
@RestController
public class MenuController implements IMenuController {

    private final MenuHandler menuHandler;

    /**
     * Instantiates a new Menu controller.
     *
     * @param menuHandler the menu handler
     */
    public MenuController(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    public ResponseEntity<BaseResponse<List<SidebarItem>>> getSideBar() {
        return ResponseEntity.ok(menuHandler.handle(GetSidebarRequest.builder().build()));
    }
}
