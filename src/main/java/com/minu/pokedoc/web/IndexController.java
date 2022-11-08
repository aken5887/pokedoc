package com.minu.pokedoc.web;

import com.minu.pokedoc.config.auth.annotation.LoginUser;
import com.minu.pokedoc.config.auth.dto.SessionUser;
import com.minu.pokedoc.domain.sticker.Sticker;
import com.minu.pokedoc.domain.user.User;
import com.minu.pokedoc.service.CategoryService;
import com.minu.pokedoc.service.StickerService;
import com.minu.pokedoc.web.dto.sticker.StickerResponseDto;
import javax.servlet.http.HttpServletRequest;
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
  public String index(Model model, HttpSession httpSession, @LoginUser SessionUser user){
    model.addAttribute("categories", categoryService.findAddDesc());
    return "index";
  }

  @GetMapping("/halloween")
  public String halloween(Model model, HttpSession httpSession,
      @RequestParam(value = "code", required = false) String requestCode,
      @LoginUser SessionUser user) {
    String code = "";

    if(user != null){
      StickerResponseDto sticker = stickerService.findByUserId(1L, user.getId());
      code = sticker.getCode();
    }else if(!StringUtils.isNullOrEmpty(requestCode)){
      code = requestCode;
    }

    model.addAttribute("code", code);

    return "pokedoc/halloween";
  }
}
