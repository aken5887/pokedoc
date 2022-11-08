package com.minu.pokedoc.domain.user;

import com.minu.pokedoc.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_user")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String picture;

  // JPA로 데이터를 저장할 때 ENUM을 어떻게 저장할지 결정
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Builder
  public User(String name, String email, String picture, Role role) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  public User update(String name, String picture){
    this.name = name;
    this.picture = picture;
    return this;
  }

  public String getRoleKey(){
    return this.role.getKey();
  }
}
