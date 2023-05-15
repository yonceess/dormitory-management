package com.diploma.user;

import com.diploma.token.Token;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  @NotBlank(message="Enter student's first name:")
  @Size(min=2, max=15, message = "Name should be between 2 and 15 characters")
  private String firstname;
  @NotBlank(message="Enter student's second name:")
  @Size(min=2, max=30, message = "Second name should be between 2 and 30 characters")
  private String lastname;
  @NotBlank(message="Enter student's dormitory':")
  private String dormitory;
  @NotBlank(message="Enter student's apartment:")
  private String apartment;
  @NotNull
  @Min(value = 1)
  @Max(value = 4)
  private Integer room;
  @NotBlank(message="Enter student's email':")
  @Email(message = "Enter valid email address")
  private String email;
  @NotBlank(message="Enter student's password':")
  @Length(min=6, message = "Password must consists of minimum 6 signs")
  private String password;
  @NotBlank(message="Enter student's idCard':")
  @Length(min=6, max=6, message = "IdCard must consists 6 signs")
  private String idCard;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDormitory() {
    return dormitory;
  }

  public String getApartment() {
    return apartment;
  }

  public Integer getRoom() {
    return room;
  }

  public void setDormitory(String dormitory) {
    this.dormitory = dormitory;
  }

  public void setApartment(String apartment) {
    this.apartment = apartment;
  }

  public void setRoom(Integer room) {
    this.room = room;
  }
}
