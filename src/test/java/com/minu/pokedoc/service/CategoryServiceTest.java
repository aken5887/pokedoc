package com.minu.pokedoc.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceTest {

  @Autowired CategoryService categoryService;

  @Test
  void path로_카테고리ID_조회(){
    //given
    String path  = "halloween";

    //when
    Long categoryId = categoryService.findCategoryIdByName(path);

    // then
    assertThat(categoryId).isGreaterThan(0L);
  }
}