package com.minu.pokedoc.config.auth.dto;

import com.minu.pokedoc.domain.user.Role;
import com.minu.pokedoc.domain.user.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
      String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
    if("kakao".equals(registrationId)){
      return ofKakao("id", attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
    // kakao는 kakao_account에 유저정보가 있음
    /**
     * {
           * "id": 2285778181,
           * "connected_at": "2022-06-14T05:37:10Z",
           * "properties":{
           * "nickname": "용훈",
           * "profile_image": "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
           * "thumbnail_image": "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"
     * },
     * "kakao_account":{
           * "profile_nickname_needs_agreement": false,
           * "profile_image_needs_agreement": false,
           * "profile":{
           * "nickname": "용훈",
           * "thumbnail_image_url": "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg",
           * "profile_image_url": "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
           * "is_default_image": true
     * },
     * "has_email": true,
     * "email_needs_agreement": false,
     * "is_email_valid": true,
     * "is_email_verified": true,
     * "email": "aken@kakao.com"
     * }
     *}
     */
    Map<String, Object> kakaoAccouint = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccouint.get("profile");

    return OAuthAttributes.builder()
        .name((String) kakaoProfile.get("nickname"))
        .email((String) kakaoAccouint.get("email"))
        .picture((String) kakaoProfile.get("profile_image_url"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public User toEntity(){
    return User.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(Role.GENERAL)
        .build();
  }
}
