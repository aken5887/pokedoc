package com.minu.pokedoc.service;

import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.domain.user.UserRepository;
import com.minu.pokedoc.web.dto.User.UserDexDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public List<UserDexDto> findAllUserDex(){
    return userRepository.findAll().stream()
        .map(UserDexDto::new)
        .collect(Collectors.toList());
  }
}
