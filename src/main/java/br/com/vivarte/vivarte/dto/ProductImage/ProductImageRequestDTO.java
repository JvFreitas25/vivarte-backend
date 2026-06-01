package br.com.vivarte.vivarte.dto.ProductImage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductImageRequestDTO(

        @NotBlank(message = "URL da imagem é obrigatória")
        String imageUrl,

        @NotNull(message = "MainImage é obrigatório")
        Boolean mainImage,

        @NotNull(message = "Produto é obrigatório")
        Integer productId
) {
}
