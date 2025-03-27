package com.ng.authen.rbac_app.model;

import lombok.Data;

/**
 * The type Sidebar item.
 */
@Data
public class SidebarItem {
    private String name;
    private String path;
    private String icon;

    /**
     * Instantiates a new Sidebar item.
     *
     * @param name the name
     * @param path the path
     * @param icon the icon
     */
    public SidebarItem(String name, String path, String icon) {
        this.name = name;
        this.path = path;
        this.icon = icon;
    }
}