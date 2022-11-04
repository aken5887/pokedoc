package com.minu.pokedoc.web.dto.category;

import com.minu.pokedoc.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

  private String name;
  private String desc;

  public CategoryResponseDto(Category category){
    this.name = category.getName();
    this.desc = category.getDesc();
  }
}
