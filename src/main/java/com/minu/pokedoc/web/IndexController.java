package com.minu.pokedoc.web;

import com.minu.pokedoc.config.auth.annotation.LoadCategories;
import com.minu.pokedoc.config.auth.annotation.LoginUser;
import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.service.CategoryService;
import com.minu.pokedoc.service.StickerService;
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
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final CategoryService categoryService;
  private final StickerService stickerService;

  @GetMapping("/")
  public String index(Model model, HttpSession httpSession,
      @LoginUser SessionUser user, @LoadCategories ArrayList<CategoryResponseDto> category){
//    model.addAttribute("categories", categoryService.findAddDesc());
    return "index";
  }

  @GetMapping("/halloween")
  public String halloween(Model model, HttpSession httpSession,
      @RequestParam(value = "code", required = false) String requestCode,
      @LoginUser SessionUser user, @LoadCategories ArrayList<CategoryResponseDto> category) {
    model.addAttribute("code", findCode(requestCode, user));
    return "pokedoc/halloween";
  }

  @GetMapping("/general")
  public String general(Model model, HttpSession httpSession,
      @RequestParam(value="code", required = false) String requestCode,
      @LoginUser SessionUser user, @LoadCategories ArrayList<CategoryResponseDto> category){
    model.addAttribute("code", findCode(requestCode, user));
    return "pokedoc/general";
  }

  private String findCode(String requestCode, SessionUser user){
    String code = "";
    if(!StringUtils.isNullOrEmpty(requestCode)){
      code = requestCode;
    } else if(user != null) {
      StickerResponseDto sticker = stickerService.findByUserId(1L, user.getId());
      code = sticker.getCode();
    }
    return code;
  }
}
