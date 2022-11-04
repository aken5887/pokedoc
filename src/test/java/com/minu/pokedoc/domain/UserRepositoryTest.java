package com.minu.pokedoc.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @AfterEach
  void cleanUp() {
    userRepository.deleteAll();
  }

  @Test
  void 유저저장_불러오기 () {
    // given
    String name = "최용훈";
    String email = "user@email.com";
    User user = User.builder()
                .name(name)
                .email(email)
                .build();

    userRepository.save(user);

    // when
    List<User> users = userRepository.findAll();

    // then
    assertEquals(users.get(0).getName(), name);
    assertEquals(users.get(0).getEmail(), email);
  }
}