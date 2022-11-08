package com.minu.pokedoc.web;

import com.minu.pokedoc.config.auth.annotation.LoginUser;
import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.service.StickerService;
import com.minu.pokedoc.web.dto.sticker.StickerRequestDto;
import com.minu.pokedoc.web.dto.sticker.StickerResponseDto;
import com.minu.pokedoc.web.dto.sticker.StickerUpdateRequestDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StickerApiController {
  private final StickerService stickerService;
  private final HttpSession httpSession;

  @PostMapping("/api/stickers")
  public Long save(@RequestBody StickerRequestDto requestDto, @LoginUser SessionUser user) {
    return stickerService.save(user, requestDto);
  }

  @PutMapping("/api/stickers/{id}")
  public Long update(@PathVariable Long id, @RequestBody StickerUpdateRequestDto requestDto){
    return stickerService.update(id, requestDto);
  }

  @GetMapping("/api/stickers/{categoryId}/{userId}")
  public StickerResponseDto findById(@PathVariable Long categoryId, @PathVariable Long userId ){
    return stickerService.findByUserId(categoryId, userId);
  }
}
