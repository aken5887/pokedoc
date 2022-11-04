package com.minu.pokedoc.domain.sticker;

import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

 Optional<Sticker> findByCategoryAndUser(Category category, User user);
}
