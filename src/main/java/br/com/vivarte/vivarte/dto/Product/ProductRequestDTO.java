package br.com.vivarte.vivarte.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Descrição é obrigatória")
        String description,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal price,

        @NotNull(message = "Estoque é obrigatório")
        Integer stock,

        @NotNull(message = "Categoria é obrigatória")
        Integer categoryId
) {
}
