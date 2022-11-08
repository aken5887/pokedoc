package com.minu.pokedoc.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.minu.pokedoc.service.CategoryService;
import com.minu.pokedoc.service.StickerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IndexControllerTest {

  @Autowired CategoryService categoryService;
  @Autowired StickerService stickerService;

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void 메인페이지_로딩(){
    // when
    String body = this.restTemplate.getForObject("/", String.class);
    // then
    assertThat(body).contains("띠부띠부씰");
  }
}