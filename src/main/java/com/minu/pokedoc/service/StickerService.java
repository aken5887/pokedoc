package com.minu.pokedoc.service;

import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.domain.sticker.Sticker;
import com.minu.pokedoc.domain.sticker.StickerRepository;
import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.domain.user.UserRepository;
import com.minu.pokedoc.web.dto.sticker.StickerRequestDto;
import com.minu.pokedoc.web.dto.sticker.StickerResponseDto;
import com.minu.pokedoc.web.dto.sticker.StickerUpdateRequestDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StickerService {

  private final StickerRepository stickerRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long save(SessionUser sessionUser, StickerRequestDto requestDto){
    Category category
        = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() ->
        new IllegalArgumentException("there is no category"));
    User user
        = userRepository.findById(sessionUser.getId()).orElseThrow(() ->
        new IllegalArgumentException("there is no user")
    );

    Optional<Sticker> sticker = stickerRepository.findByCategoryAndUser(category, user);
    if(!sticker.isPresent()){
      return stickerRepository.save(requestDto.toEntity(user, category)).getId();
    }else{
      sticker.get().update(requestDto.getCode());
      return sticker.get().getId();
    }
  }

  @Transactional
  public Long update(Long id, StickerUpdateRequestDto requestDto) {
    Sticker sticker = stickerRepository.findById(id).orElseThrow(()->
        new IllegalArgumentException("there is no sticker information"));
    sticker.update(requestDto.getCode());
    return id;
  }

  public StickerResponseDto findByUserId(Long categoryId, Long userId){

    Category category
        = categoryRepository.findById(categoryId).orElseThrow(() ->
        new IllegalArgumentException("there is no category"));
    User user
        = userRepository.findById(userId).orElseThrow(() ->
        new IllegalArgumentException("there is no user")
    );
   Optional<Sticker> sticker = stickerRepository.findByCategoryAndUser(category, user);

   if(sticker.isPresent()){
     return new StickerResponseDto(sticker.get());
   }else{
     Sticker newSticker = Sticker.builder()
         .code(null)
         .user(user)
         .category(category)
         .build();
     return new StickerResponseDto(newSticker);
   }
  }
}
