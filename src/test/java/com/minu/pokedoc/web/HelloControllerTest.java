package com.minu.pokedoc.web;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class HelloControllerTest {

  @Autowired MockMvc mockMvc;

  @Test
  void Hello가_리턴된다() throws Exception {
    String hello = "hello";

    mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(hello)));
  }

  @Test
  void HelloDto가_리턴된다() throws Exception {
    String name = "hello";
    int amount = 1000;

    mockMvc.perform(MockMvcRequestBuilders.get("/hello/dto")
        .param("name", name)
        .param("amount", String.valueOf(amount)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.amount").value(amount));
  }
}