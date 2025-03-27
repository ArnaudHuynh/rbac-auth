package com.ng.authen.rbac_app.repository;

import com.ng.authen.rbac_app.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Permission repository.
 */
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<PermissionEntity> findByName(String name);
}