package com.minu.pokedoc.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceTest {

  @Autowired CategoryRepository categoryRepository;
  @Autowired CategoryService categoryService;
  private Long categoryId;

  @BeforeEach
  void setup(){
    Category category = Category.builder().name("winter").build();
    categoryId = categoryRepository.save(category).getId();
  }

  @Test
  void path로_카테고리ID_조회(){
    //given
    String path  = "winter";

    //when
    Long categoryId = categoryService.findCategoryIdByName(path);

    // then
    assertThat(categoryId).isEqualTo(categoryId);
  }
}