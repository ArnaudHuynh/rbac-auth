package com.ng.authen.rbac_app.model;

import lombok.Data;

@Data
public class SidebarItem {
    private String name;
    private String path;
    private String icon;

    public SidebarItem(String name, String path, String icon) {
        this.name = name;
        this.path = path;
        this.icon = icon;
    }
}