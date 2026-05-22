package org.sopt.domain.auth.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final ConcurrentHashMap<String, Instant> blacklist = new ConcurrentHashMap<>();

    public void addToBlacklist(String token, Instant expiresAt) {
        blacklist.put(token, expiresAt);
    }

    public boolean isBlacklisted(String token) {
        Instant expiresAt = blacklist.get(token);
        if (expiresAt == null) return false;
        if (Instant.now().isAfter(expiresAt)) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }

    // 1시간마다 만료된 토큰 정리
    @Scheduled(fixedRate = 3600000)
    public void cleanUp() {
        Instant now = Instant.now();
        blacklist.entrySet().removeIf(entry -> now.isAfter(entry.getValue()));
    }
}