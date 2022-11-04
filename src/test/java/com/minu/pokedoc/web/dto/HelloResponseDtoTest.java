package com.minu.pokedoc.web.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HelloResponseDtoTest {

  @Test
  void 롬복_기능_테스트(){
    //given
    String name = "test";
    int amount = 1;

    //when
    HelloResponseDto dto = new HelloResponseDto("test", 1);

    //then
    assertThat(dto.getName()).isEqualTo(name);
    assertEquals(dto.getAmount(), amount);
  }

}