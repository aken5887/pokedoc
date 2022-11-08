package com.minu.pokedoc.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

  GENERAL("ROLE_GENERAL", "일반"),
  ADMIN("ROLE_ADMIN", "관리자");

  private final String key;
  private final String title;
}
