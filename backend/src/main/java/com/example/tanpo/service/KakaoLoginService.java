package com.example.tanpo.service;

import com.example.tanpo.entity.KakaoUserEntity;
import com.example.tanpo.repository.KakaoUserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    @Value("${kakao.token.uri:https://kauth.kakao.com/oauth/token}")
    private String tokenUri;

    @Value("${kakao.userinfo.uri:https://kapi.kakao.com/v2/user/me}")
    private String userInfoUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoUserRepository kakaoUserRepository;

    /** 인가 코드 → 액세스/리프레시 토큰 교환 */
    public JsonObject getAccessToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);

        return JsonParser.parseString(response.getBody()).getAsJsonObject();
    }

    /** 액세스 토큰으로 카카오 프로필 조회 */
    public JsonObject getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, request, String.class
        );
        return JsonParser.parseString(response.getBody()).getAsJsonObject();
    }

    public KakaoUserEntity upsertUser(JsonObject tokenJson, JsonObject profileJson) {
        // 토큰
        String accessToken = tokenJson.has("access_token") && !tokenJson.get("access_token").isJsonNull()
                ? tokenJson.get("access_token").getAsString() : null;
        String refreshToken = tokenJson.has("refresh_token") && !tokenJson.get("refresh_token").isJsonNull()
                ? tokenJson.get("refresh_token").getAsString() : null;

        // 사용자 기본 식별자
        long kakaoId = profileJson.get("id").getAsLong();

        // kakao_account/profile 파싱 (이메일은 없을 수 있음)
        JsonObject kakaoAccount = safeObj(profileJson.get("kakao_account"));
        JsonObject profile = safeObj(kakaoAccount.get("profile"));

        String nickname = safeString(profile.get("nickname"));
        String profileImage = safeString(profile.get("profile_image_url"));
        String email = safeString(kakaoAccount.get("email")); // scope에서 제거했으면 대부분 null

        // upsert
        KakaoUserEntity user = kakaoUserRepository.findByKakaoId(kakaoId).orElseGet(KakaoUserEntity::new);
        if (user.getId() == null) {
            user.setKakaoId(kakaoId);
        }
        user.setNickname(nickname);
        user.setProfileImage(profileImage);
        user.setEmail(email);
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);

        return kakaoUserRepository.save(user);
    }

    // --------- JSON 안전 유틸 ---------
    private JsonObject safeObj(JsonElement el) {
        return (el != null && el.isJsonObject()) ? el.getAsJsonObject() : new JsonObject();
    }
    private String safeString(JsonElement el) {
        return (el != null && !el.isJsonNull()) ? el.getAsString() : null;
    }
}
