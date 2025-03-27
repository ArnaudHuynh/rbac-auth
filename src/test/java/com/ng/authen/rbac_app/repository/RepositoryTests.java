//package com.ng.authen.rbac_app.repository;
//
//import com.ng.authen.rbac_app.entity.UserEntity;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
///**
// * The type Repository tests.
// */
//@SpringBootTest
//@Transactional
//public class RepositoryTests {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * Test find by username.
//     */
//    @Test
//    public void testFindByUsername() {
//        Optional<UserEntity> user = userRepository.findByUsername("admin");
//        assertTrue(user.isPresent());
//        assertTrue(user.get().getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN")));
//    }
//
//    /**
//     * Test password encoding and matching.
//     */
//    @Test
//    public void testPasswordEncodingAndMatching() {
//        Optional<UserEntity> user = userRepository.findByUsername("admin");
//        assertTrue(user.isPresent());
//
//        // Verify the password
//        String rawPassword = "admin123";
//        String encodedPassword = user.get().getPassword();
//        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
//
//        // Verify a wrong password fails
//        assertFalse(passwordEncoder.matches("wrongpassword", encodedPassword));
//    }
//}