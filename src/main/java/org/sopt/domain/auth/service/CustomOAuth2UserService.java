package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.security.CustomOAuth2User;
import org.sopt.domain.auth.security.KakaoOAuth2UserInfo;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        KakaoOAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());

        User user = userRepository.findByKakaoId(userInfo.getId())
                .orElseGet(() -> userRepository.save(
                        User.ofKakao(userInfo.getId(), userInfo.getNickname(), userInfo.getEmail())
                ));

        return new CustomOAuth2User(oAuth2User, user.getId());
    }
}
