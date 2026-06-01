package br.com.vivarte.vivarte.dto.Product;

import br.com.vivarte.vivarte.dto.Category.CategorySimpleResponseDTO;
import br.com.vivarte.vivarte.dto.ProductImage.ProductImageResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(

        Integer id,

        String name,

        String description,

        BigDecimal price,

        Integer stock,

        CategorySimpleResponseDTO category,

        List<ProductImageResponseDTO> images,

        LocalDateTime createdAt
) {
}
