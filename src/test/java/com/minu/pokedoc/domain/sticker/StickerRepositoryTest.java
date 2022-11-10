package com.minu.pokedoc.domain.sticker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.domain.user.Role;
import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StickerRepositoryTest {

  @Autowired
  StickerRepository stickerRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  CategoryRepository categoryRepository;

  Long userId;

  Long cateogryId;

  final String DEFAULT_CODE = "kdkfdkdk1k21k3128fj";

  @BeforeEach
  void setUp(){
    User user = User.builder().name("test").email("test.com").role(Role.GENERAL).build();
    this.userId = userRepository.save(user).getId();
    Category category = Category.builder().name("test").build();
    this.cateogryId = categoryRepository.save(category).getId();
  }

  @AfterEach
  void clear(){
    stickerRepository.deleteAll();
    userRepository.deleteAll();
    categoryRepository.deleteAll();
  }


  @Test
  void BaseTimeEntity_등록(){
    // given
    LocalDateTime now = LocalDateTime.now();
    stickerRepository.save(Sticker.builder()
                        .code(DEFAULT_CODE)
                        .user(userRepository.findById(this.userId).get())
                        .category(categoryRepository.findById(this.cateogryId).get())
                        .build());

    // when
    List<Sticker> stickers = stickerRepository.findAll();

    //then
    Sticker sticker = stickers.get(0);
    System.out.println(sticker.toString());
    assertThat(sticker.getCreateTime()).isAfter(now);
    assertThat(sticker.getModifiedTime()).isAfter(now);
  }

}