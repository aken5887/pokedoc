package com.minu.pokedoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing // jpa auditing 활성화
@SpringBootApplication
public class PokedocApplication {

  public static void main(String[] args) {
    SpringApplication.run(PokedocApplication.class, args);
  }

}
