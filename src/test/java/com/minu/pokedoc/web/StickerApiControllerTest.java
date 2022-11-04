package com.minu.pokedoc.web;

import static org.junit.jupiter.api.Assertions.*;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.domain.sticker.Sticker;
import com.minu.pokedoc.domain.sticker.StickerRepository;
import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.domain.user.UserRepository;
import com.minu.pokedoc.web.dto.sticker.StickerRequestDto;
import com.minu.pokedoc.web.dto.sticker.StickerResponseDto;
import com.minu.pokedoc.web.dto.sticker.StickerUpdateRequestDto;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StickerApiControllerTest {

  @LocalServerPort private int port;

  @Autowired
  TestRestTemplate testRestTemplate;

  @Autowired
  StickerRepository stickerRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  CategoryRepository categoryRepository;

  Long userId;

  Long cateogryId;

  final String DEFAULT_CODE = "kdkfdkdk1k21k3128fj";

  @BeforeEach
  void setUp(){
    User user = User.builder().name("test").build();
    this.userId = userRepository.save(user).getId();
    Category category = Category.builder().name("halloween").build();
    this.cateogryId = categoryRepository.save(category).getId();
  }

  @Test
  void Sticker_등록된다() throws Exception {
    // given
    StickerRequestDto requestDto = StickerRequestDto.builder()
        .code(DEFAULT_CODE)
        .categoryId(this.cateogryId)
        .userId(this.userId)
        .build();
    String url = "http://localhost:"+port+"/api/stickers";

    //when
    ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

    //then
    Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    Assertions.assertTrue(responseEntity.getBody() > 0L );

    List<Sticker> stickers = stickerRepository.findAll();
    assertEquals(stickers.get(0).getCode(), DEFAULT_CODE);
  }

  @Test
  void Sticker_수정된다() {
    // given
    Sticker savedSticker = stickerRepository.save(makeDefaultSticker());
    Long updatedId = savedSticker.getId();
    String expectedCode = "11111111111111";

    StickerUpdateRequestDto requestDto = StickerUpdateRequestDto.builder()
                                                                .code(expectedCode)
                                                                .build();
    String url =
        "http://localhost:" + port + "/api/stickers/" + updatedId;
    HttpEntity<StickerUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    //when
    ResponseEntity<?> responseEntity
        = this.testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);

    //then
    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    assertTrue(Long.parseLong(responseEntity.getBody().toString()) > 0L);

    List<Sticker> stickers = stickerRepository.findAll();
    assertEquals(stickers.get(0).getCode(), expectedCode);
  }

  @Test
  void Sticker_조회한다(){
    // given
    Sticker savedSticker = stickerRepository.save(makeDefaultSticker());
    String url = "http://localhost:"+port+"/api/stickers/"+cateogryId+"/"+userId;
    //when
    ResponseEntity<StickerResponseDto> responseEntity
        = this.testRestTemplate.exchange(url, HttpMethod.GET, null, StickerResponseDto.class);
    StickerResponseDto responseDto = responseEntity.getBody();
    //then
    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    assertEquals(responseDto.getCode(), savedSticker.getCode());
  }

  private Sticker makeDefaultSticker(){
    return Sticker.builder()
        .code(DEFAULT_CODE)
        .user(userRepository.findById(this.userId).get())
        .category(categoryRepository.findById(this.cateogryId).get())
        .build();
  }
}