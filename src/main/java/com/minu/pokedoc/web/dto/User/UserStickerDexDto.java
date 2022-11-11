package com.minu.pokedoc.web.dto.User;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.sticker.Sticker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserStickerDexDto {
  private Long categoryId;
  private String categoryName;
  private String categoryDesc;
  private String code;

  @Builder
  public UserStickerDexDto(Long categoryId, String categoryName, String categoryDesc, String code) {
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.categoryDesc = categoryDesc;
    this.code = code;
  }
}
