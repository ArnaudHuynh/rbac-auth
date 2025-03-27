//package com.ng.authen.rbac_app.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ng.authen.rbac_app.entity.RoleEntity;
//import com.ng.authen.rbac_app.entity.UserEntity;
//import com.ng.authen.rbac_app.model.request.LoginRequest;
//import com.ng.authen.rbac_app.model.request.RegisterRequest;
//import com.ng.authen.rbac_app.model.response.BaseResponse;
//import com.ng.authen.rbac_app.model.response.LoginResponse;
//import com.ng.authen.rbac_app.repository.RoleRepository;
//import com.ng.authen.rbac_app.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Set;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * The type Auth controller test.
// */
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("dev")
//public class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    /**
//     * Sets up.
//     */
//    @BeforeEach
//    public void setUp() {
//        // Clear the database
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
//
//        // Create roles
//        roleRepository.save(RoleEntity.builder().name("USER").build());
//
//        RoleEntity adminRole = roleRepository.save(RoleEntity.builder().name("ADMIN").build());
//
//        // Create an admin user
//        UserEntity admin = UserEntity.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin123"))
//                .email("admin@example.com")
//                .roles(Set.of(adminRole))
//                .build();
//        userRepository.save(admin);
//    }
//
//    /**
//     * Test register success.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testRegister_Success() throws Exception {
//        RegisterRequest request = RegisterRequest.builder()
//                .username("newuser")
//                .password("password123")
//                .email("newuser@example.com")
//                .build();
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value("User registered successfully"))
//                .andExpect(jsonPath("$.requestId").isNotEmpty());
//    }
//
//    /**
//     * Test register username taken.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testRegister_UsernameTaken() throws Exception {
//        RegisterRequest request = RegisterRequest.builder()
//                .username("admin")                      // Already exists
//                .password("password123")
//                .email("newuser@example.com")
//                .build();
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Username is already taken"))
//                .andExpect(jsonPath("$.requestId").isNotEmpty());
//    }
//
//    /**
//     * Test login success.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testLogin_Success() throws Exception {
//        LoginRequest request = LoginRequest.builder()
//                .username("admin")
//                .password("admin123")
//                .build();
//
//        mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.token").isNotEmpty())
//                .andExpect(jsonPath("$.requestId").isNotEmpty());
//    }
//
//    /**
//     * Test login invalid credentials.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testLogin_InvalidCredentials() throws Exception {
//        LoginRequest request = LoginRequest.builder()
//                .username("admin")
//                .password("wrongpassword")
//                .build();
//
//        mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.message").value("Authentication failed: Bad credentials"))
//                .andExpect(jsonPath("$.requestId").isNotEmpty());
//    }
//
//    /**
//     * Test logout success.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testLogout_Success() throws Exception {
//        // First, log in to get a token
//        LoginRequest loginRequest = LoginRequest.builder()
//                .username("admin")
//                .password("admin123")
//                .build();
//
//        String loginResponse = mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        BaseResponse<LoginResponse> baseResponse =
//                objectMapper.readValue(
//                        loginResponse,
//                        objectMapper.getTypeFactory().constructParametricType(BaseResponse.class, LoginResponse.class)
//                );
//        String token = baseResponse.getData().getToken();
//
//        // Now, test logout
//        mockMvc.perform(post("/api/auth/logout")
//                        .header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value("Logged out successfully"))
//                .andExpect(jsonPath("$.requestId").isNotEmpty());
//    }
//
//    /**
//     * Test logout missing token.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    public void testLogout_MissingToken() throws Exception {
//        mockMvc.perform(post("/api/auth/logout"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid or missing token"))
//                .andExpect(jsonPath("$.requestId").isNotEmpty());
//    }
//}