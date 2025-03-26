package com.ng.authen.rbac_app.config;


import com.ng.authen.rbac_app.entity.PermissionEntity;
import com.ng.authen.rbac_app.entity.RoleEntity;
import com.ng.authen.rbac_app.entity.UserEntity;
import com.ng.authen.rbac_app.repository.PermissionRepository;
import com.ng.authen.rbac_app.repository.RoleRepository;
import com.ng.authen.rbac_app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      PermissionRepository permissionRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Create Permissions
            PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();
            PermissionEntity writePermission = PermissionEntity.builder().name("WRITE").build();
            PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();

            permissionRepository.save(readPermission);
            permissionRepository.save(writePermission);
            permissionRepository.save(deletePermission);

            // Create Roles
            RoleEntity adminRole = RoleEntity.builder()
                    .name("ADMIN")
                    .permissions(Set.of(readPermission, writePermission, deletePermission))
                    .build();

            RoleEntity userRole = RoleEntity.builder()
                    .name("USER")
                    .permissions(Set.of(readPermission))
                    .build();

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            // Create a default admin UserEntity
            UserEntity adminUser = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123")) // Encode the password
                    .email("admin@example.com")
                    .roles(Set.of(adminRole))
                    .build();

            // Create a default regular UserEntity
            UserEntity regularUser = UserEntity.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .email("user@example.com")
                    .roles(Set.of(userRole))
                    .build();

            userRepository.save(adminUser);
            userRepository.save(regularUser);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Configure Argon2 with custom parameters
        return new Argon2PasswordEncoder(
                16,               // Salt length (bytes)
                32,                         // Hash length (bytes)
                1,                          // Parallelism (number of threads)
                32521,                      // Memory cost (in kibibytes, 64 MB)
                1                           // Iterations
        );
    }
}