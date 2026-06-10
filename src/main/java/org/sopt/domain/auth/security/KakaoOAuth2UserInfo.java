package org.sopt.domain.auth.security;

import java.util.Map;

public class KakaoOAuth2UserInfo {

    private final Long id;
    private final String nickname;
    private final String email;

    @SuppressWarnings("unchecked")
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.id = ((Number) attributes.get("id")).longValue();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null
                ? (Map<String, Object>) kakaoAccount.get("profile")
                : null;

        this.nickname = profile != null ? (String) profile.get("nickname") : null;
        this.email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
    }

    public Long getId() { return id; }
    public String getNickname() { return nickname; }
    public String getEmail() { return email; }
}
