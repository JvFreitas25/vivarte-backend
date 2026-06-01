package br.com.vivarte.vivarte.dto.User;

import br.com.vivarte.vivarte.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password,

        @NotBlank(message = "Telefone é obrigatória")
        String phone,

        @NotNull(message = "Role é obrigatória")
        Role role
) {
}
