package com.minu.pokedoc.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.domain.sticker.Sticker;
import com.minu.pokedoc.domain.sticker.StickerRepository;
import com.minu.pokedoc.domain.user.Role;
import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.domain.user.UserRepository;
import com.minu.pokedoc.web.dto.sticker.StickerRequestDto;
import com.minu.pokedoc.web.dto.sticker.StickerUpdateRequestDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

  private MockMvc mockMvc;

  private MockHttpSession httpSession;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setUp(){
    User user = User.builder().name("test").email("test.com").role(Role.GENERAL).build();
    this.userId = userRepository.save(user).getId();
    Category category = Category.builder().name("test").build();
    this.cateogryId = categoryRepository.save(category).getId();

    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

    httpSession = new MockHttpSession();
    httpSession.setAttribute("user", new SessionUser(user));
  }

  @AfterEach
  void clear(){
    httpSession.clearAttributes();
    stickerRepository.deleteAll();
    userRepository.deleteAll();
    categoryRepository.deleteAll();
  }

  @Test
  @WithMockUser(roles="GENERAL") // ROLE_GENERAL 권한을 가진 사용자가 API를 요청하는 것과 동일
  void Sticker_등록된다() throws Exception {

    // given
    StickerRequestDto requestDto = StickerRequestDto.builder()
        .code(DEFAULT_CODE)
        .categoryId(this.cateogryId)
        .userId(this.userId)
        .build();

    String url = "http://localhost:"+port+"/api/stickers";

    // security 적용 전
//    //when
//    ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);
//
//    //then
//    Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//    Assertions.assertTrue(responseEntity.getBody() > 0L );

    // security 적용 후
    // when
    mockMvc.perform(post(url).session(httpSession)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());

    // then
    List<Sticker> stickers = stickerRepository.findAll();
    assertEquals(stickers.get(0).getCode(), DEFAULT_CODE);
  }

  @Test
  @WithMockUser(roles="GENERAL")
  void Sticker_수정된다() throws Exception {
    // given
    Sticker savedSticker = stickerRepository.save(makeDefaultSticker());
    Long updatedId = savedSticker.getId();
    String expectedCode = "11111111111111";

    StickerUpdateRequestDto requestDto = StickerUpdateRequestDto.builder()
                                                                .code(expectedCode)
                                                                .build();
    String url =
        "http://localhost:" + port + "/api/stickers/" + updatedId;

    // spring security 전
//    HttpEntity<StickerUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//    //when
//    ResponseEntity<?> responseEntity
//        = this.testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
//
//    //then
//    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//    assertTrue(Long.parseLong(responseEntity.getBody().toString()) > 0L);

    // spring security 적용 후
    mockMvc.perform(put(url).session(httpSession)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());

    List<Sticker> stickers = stickerRepository.findAll();
    assertEquals(stickers.get(0).getCode(), expectedCode);
  }

  @Test
  @WithMockUser(roles="GENERAL")
  void Sticker_조회한다() throws Exception {
    // given
    Sticker savedSticker = stickerRepository.save(makeDefaultSticker());
    String url = "http://localhost:"+port+"/api/stickers/"+cateogryId+"/"+userId;
    //when
//    ResponseEntity<StickerResponseDto> responseEntity
//        = this.testRestTemplate.exchange(url, HttpMethod.GET, null, StickerResponseDto.class);
//    StickerResponseDto responseDto = responseEntity.getBody();
//    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//    assertEquals(responseDto.getCode(), savedSticker.getCode());

    mockMvc.perform(MockMvcRequestBuilders.get(url)
        .session(httpSession))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(savedSticker.getCode()));
    //then

  }

  private Sticker makeDefaultSticker(){
    return Sticker.builder()
        .code(DEFAULT_CODE)
        .user(userRepository.findById(this.userId).get())
        .category(categoryRepository.findById(this.cateogryId).get())
        .build();
  }
}