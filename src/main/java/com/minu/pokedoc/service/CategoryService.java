package com.minu.pokedoc.service;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.category.CategoryRepository;
import com.minu.pokedoc.web.dto.category.CategoryResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public List<CategoryResponseDto> findAddDesc(){
    return categoryRepository.findAll().stream()
        .map(CategoryResponseDto::new)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Long findCategoryIdByName(String name){
    return categoryRepository.findByName(name).orElseThrow(() -> {
      throw new IllegalArgumentException("there is no category by name = "+name);
    }).getId();
  }
}
