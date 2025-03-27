package com.ng.authen.rbac_app.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Token blacklist service.
 */
@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = new HashSet<>();

    /**
     * Blacklist token.
     *
     * @param token the token
     */
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Is token blacklisted boolean.
     *
     * @param token the token
     * @return the boolean
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}