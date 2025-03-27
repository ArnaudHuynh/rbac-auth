package com.ng.authen.rbac_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ng.authen.rbac_app.entity.RoleEntity;
import com.ng.authen.rbac_app.entity.UserEntity;
import com.ng.authen.rbac_app.model.request.LoginRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import com.ng.authen.rbac_app.model.response.LoginResponse;
import com.ng.authen.rbac_app.repository.RoleRepository;
import com.ng.authen.rbac_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Test controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;
    private String userToken;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    @Transactional
    public void setUp() throws Exception {
        // Clear the database
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create roles
        RoleEntity userRole = RoleEntity.builder()
                .name("USER")
                .build();
        userRole = roleRepository.save(userRole);

        RoleEntity adminRole = RoleEntity.builder()
                .name("ADMIN")
                .build();
        adminRole = roleRepository.save(adminRole);

        // Create an admin user
        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@example.com")
                .roles(Set.of(adminRole))
                .build();
        userRepository.save(admin);

        // Create a regular user
        UserEntity user = UserEntity.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .email("user@example.com")
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);

        // Log in as admin to get a token
        LoginRequest adminLogin = LoginRequest.builder()
                .username("admin")
                .password("admin123")
                .build();
        String adminLoginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminLogin)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<LoginResponse> adminBaseResponse =
                objectMapper.readValue(
                        adminLoginResponse,
                        objectMapper.getTypeFactory().constructParametricType(BaseResponse.class, LoginResponse.class)
                );
        adminToken = adminBaseResponse.getData().getToken();

        // Log in as user to get a token
        LoginRequest userLogin = LoginRequest.builder()
                .username("user")
                .password("user123")
                .build();
        String userLoginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogin)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<LoginResponse> userBaseResponse =
                objectMapper.readValue(
                        userLoginResponse,
                        objectMapper.getTypeFactory().constructParametricType(BaseResponse.class, LoginResponse.class)
                );
        userToken = userBaseResponse.getData().getToken();
    }

    /**
     * Test admin endpoint as admin.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAdminEndpoint_AsAdmin() throws Exception {
        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Hello, Admin!"))
                .andExpect(jsonPath("$.requestId").isNotEmpty());
    }

    /**
     * Test admin endpoint as user.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAdminEndpoint_AsUser() throws Exception {
        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied: You do not have permission to access this resource"))
                .andExpect(jsonPath("$.requestId").isNotEmpty());
    }

    /**
     * Test user endpoint as admin.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUserEndpoint_AsAdmin() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Hello, User!"))
                .andExpect(jsonPath("$.requestId").isNotEmpty());
    }

    /**
     * Test user endpoint as user.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUserEndpoint_AsUser() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Hello, User!"))
                .andExpect(jsonPath("$.requestId").isNotEmpty());
    }

    /**
     * Test user endpoint unauthenticated.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUserEndpoint_Unauthenticated() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authentication failed: Full authentication is required to access this resource"))
                .andExpect(jsonPath("$.requestId").isNotEmpty());
    }
}