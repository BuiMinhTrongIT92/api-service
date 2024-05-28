package com.trong.apiservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRequestDTO {
    @Valid
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @Valid
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
