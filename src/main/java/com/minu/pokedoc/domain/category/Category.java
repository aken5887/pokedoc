package com.minu.pokedoc.domain.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name="tb_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String desc;

  @Builder
  public Category(Long id, String name, String desc) {
    this.id = id;
    this.name = name;
    this.desc = desc;
  }
}
