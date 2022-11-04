package com.minu.pokedoc.web.dto.sticker;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@NoArgsConstructor
public class StickerUpdateRequestDto {
  private String code;
  @Builder
  public StickerUpdateRequestDto(String code) {
    this.code = code;
  }
}
