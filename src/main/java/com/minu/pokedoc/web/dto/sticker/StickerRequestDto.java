package com.minu.pokedoc.web.dto.sticker;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.domain.sticker.Sticker;
import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Service
public class StickerRequestDto {

  private String code;
  private Long categoryId;
  private Long userId;

  @Builder
  public StickerRequestDto(String code, Long categoryId, Long userId) {
    this.code = code;
    this.categoryId = categoryId;
    this.userId = userId;
  }

  public Sticker toEntity(User user, Category category){
    return Sticker.builder()
        .code(this.code)
        .user(user)
        .category(category)
        .build();
  }
}
