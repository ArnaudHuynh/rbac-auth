package com.ng.authen.rbac_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AD_PERMISSION")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<RoleEntity> roles = new HashSet<>();

    // Exclude 'roles' from equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionEntity permission = (PermissionEntity) o;

        return id != null && id.equals(permission.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}