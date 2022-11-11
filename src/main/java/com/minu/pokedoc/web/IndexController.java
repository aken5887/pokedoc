package com.minu.pokedoc.web;

import com.minu.pokedoc.config.auth.annotation.LoadCategories;
import com.minu.pokedoc.config.auth.annotation.LoginUser;
import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.service.CategoryService;
import com.minu.pokedoc.service.StickerService;
import com.minu.pokedoc.service.UserService;
import com.minu.pokedoc.web.dto.User.UserDexDto;
import com.minu.pokedoc.web.dto.User.UserStickerDexDto;
import com.minu.pokedoc.web.dto.category.CategoryResponseDto;
import com.minu.pokedoc.web.dto.sticker.StickerResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final CategoryService categoryService;
  private final StickerService stickerService;
  private final UserService userService;

  @GetMapping("/")
  public String index(Model model, HttpSession httpSession,
      @LoginUser SessionUser user, @LoadCategories ArrayList<CategoryResponseDto> category){
//    model.addAttribute("categories", categoryService.findAddDesc());
    return "index";
  }

  @GetMapping("/pokedex/{path}")
  public String halloween(Model model, HttpSession httpSession,
      @PathVariable String path,
      @RequestParam(value = "code", required = false) String requestCode,
      @LoginUser SessionUser user, @LoadCategories ArrayList<CategoryResponseDto> category) {
      Long categoryId = categoryService.findCategoryIdByName(path);
      model.addAttribute("categoryId", categoryId);
      model.addAttribute("code", findCode(requestCode, categoryId, user));
      return "pokedoc/"+path;
  }

  @GetMapping("/explorer")
  public String explorer(Model model, @LoginUser SessionUser user,
      @LoadCategories ArrayList<CategoryResponseDto> categories){
    List<UserDexDto> userDexes = userService.findAllUserDex();
    for(UserDexDto userDex : userDexes){
      List<UserStickerDexDto> stickerDexDtos = new ArrayList<>();
        for(CategoryResponseDto category : categories){
          Long categoryId = categoryService.findCategoryIdByName(category.getName());
          StickerResponseDto sticker = stickerService.findByUserId(categoryId, userDex.getId());
          stickerDexDtos.add(UserStickerDexDto.builder()
                  .categoryId(categoryId)
                  .categoryName(category.getName())
                  .categoryDesc(category.getDesc())
                  .code(sticker!=null?sticker.getCode():"")
                  .build());
        }
      userDex.update(stickerDexDtos);
    }
    model.addAttribute("userDexes", userDexes);
    return "pokedoc/explorer";
  }


  private String findCode(String requestCode, Long categoryId, SessionUser user){
    String code = "";
    if(!StringUtils.isNullOrEmpty(requestCode)){
      code = requestCode;
    } else if(user != null) {
      StickerResponseDto sticker = stickerService.findByUserId(categoryId, user.getId());
      code = sticker.getCode();
    }
    return code;
  }
}
