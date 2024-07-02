package com.phil.einvoicing.auth;

import com.phil.einvoicing.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String names;
  private String username;
  private String password;
  private Role role;
}
