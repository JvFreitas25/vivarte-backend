package br.com.vivarte.vivarte.dto.User;

import br.com.vivarte.vivarte.enums.Role;

import java.time.LocalDateTime;

public record UserResponseDTO(

        Integer id,

        String name,

        String email,

        LocalDateTime createdAt,

        Role role
) {
}
