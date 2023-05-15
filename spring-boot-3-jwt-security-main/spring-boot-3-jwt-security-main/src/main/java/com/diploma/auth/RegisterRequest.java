package com.diploma.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String dormitory;
  private String apartment;
  private Integer room;
  private String email;
  private String password;

  private String idCard;
}
