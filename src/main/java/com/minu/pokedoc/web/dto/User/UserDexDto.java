package com.minu.pokedoc.web.dto.User;

import com.minu.pokedoc.domain.user.User;
import java.util.List;
import lombok.Getter;

@Getter
public class UserDexDto {
  private Long id;
  private String name;
  private String picture;
  private List<UserStickerDexDto> stickers;

  public UserDexDto(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.picture = user.getPicture();
  }

  public void update(List<UserStickerDexDto> stickers){
    this.stickers = stickers;
  }
}
