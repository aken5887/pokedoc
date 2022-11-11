package com.minu.pokedoc.web.dto.User;

import com.minu.pokedoc.domain.user.User;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDexDto {
  private Long id;
  private String name;
  private String picture;
  private List<UserStickerDexDto> stickers;

  public UserDexDto(User user) {
    this.id = user.getId();
    this.name = convertUserName(user.getName());
    this.picture = user.getPicture();
  }

  public void update(List<UserStickerDexDto> stickers){
    this.stickers = stickers;
  }

  private String convertUserName(String userName){
    String newName = "";
    switch(userName.length()){
      case 2:
      case 3:
        newName = userName.replace(userName.charAt(1), '*');
        break;
      case 4:
        newName = userName.replace(userName.charAt(1), '*')
            .replace(userName.charAt(2), '*');
        break;
      default:
        if(userName.length()>4){
          newName = userName.replace(userName.charAt(1), '*')
              .replace(userName.charAt(2), '*')
              .replace(userName.charAt(3), '*');
        }else{
          newName = userName;
        }
        break;
    }
    return newName;
  }
}