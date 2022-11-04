package com.minu.pokedoc.web.dto.sticker;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.sticker.Sticker;
import com.minu.pokedoc.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StickerResponseDto {

  private String code;
  private User user;
  private Category category;

  public StickerResponseDto(Sticker entity){
    this.code = entity.getCode();
    this.user = entity.getUser();
    this.category = entity.getCategory();
  }
}
