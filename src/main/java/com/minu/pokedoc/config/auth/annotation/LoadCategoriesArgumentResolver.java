package com.minu.pokedoc.config.auth.annotation;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.service.CategoryService;
import com.minu.pokedoc.web.dto.category.CategoryResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoadCategoriesArgumentResolver implements HandlerMethodArgumentResolver {

  private final CategoryService categoryService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean result = parameter.getParameterAnnotation(LoadCategories.class) != null &
        parameter.getParameterType().equals(ArrayList.class);
    return result;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    List<CategoryResponseDto> categories = categoryService.findAddDesc();
    mavContainer.addAttribute("categories", categories);
    return categories;
  }
}
