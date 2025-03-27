package com.ng.authen.rbac_app.repository;

import com.ng.authen.rbac_app.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<RoleEntity> findByName(String name);
}