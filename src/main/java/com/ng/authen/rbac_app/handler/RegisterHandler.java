package com.ng.authen.rbac_app.handler;

import com.ng.authen.rbac_app.entity.RoleEntity;
import com.ng.authen.rbac_app.entity.UserEntity;
import com.ng.authen.rbac_app.exception.BadRequestException;
import com.ng.authen.rbac_app.exception.ResourceNotFoundException;
import com.ng.authen.rbac_app.model.request.RegisterRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import com.ng.authen.rbac_app.repository.RoleRepository;
import com.ng.authen.rbac_app.repository.UserRepository;
import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RegisterHandler implements HandlerWrapper<RegisterRequest, BaseResponse<String>> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterHandler(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseResponse<String> handle(RegisterRequest request) {
        // Validate input
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        // Create new user
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        // Assign the USER role by default
        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("USER role not found"));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        return BaseResponse.<String>builder()
                .requestId(MDC.get("requestId"))
                .success(true)
                .data("User registered successfully")
                .build();
    }
}
