package com.minu.pokedoc.domain.sticker;

import com.minu.pokedoc.domain.BaseTimeEntity;
import com.minu.pokedoc.domain.category.Category;
import com.minu.pokedoc.domain.user.User;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name="tb_sticker")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"category_id","user_id"})})
public class Sticker extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;

  @ManyToOne
  private Category category;

  @ManyToOne
  private User user;

  @Builder
  public Sticker(String code, Category category, User user) {
    this.code = code;
    this.category = category;
    this.user = user;
  }

  public void update(String code){
    this.code = code;
  }

  @Override
  public String toString() {
    return "Sticker{" +
        "id=" + id +
        ", code='" + code + '\'' +
        ", category=" + category +
        ", user=" + user +
        ", time=" + getCreateTime() + " / " +getModifiedTime() +
        '}';
  }
}
