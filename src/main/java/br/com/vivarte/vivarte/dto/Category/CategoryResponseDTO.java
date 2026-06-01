package br.com.vivarte.vivarte.dto.Category;

import br.com.vivarte.vivarte.dto.Product.ProductSimpleResponseDTO;

import java.util.List;

public record CategoryResponseDTO(

        Integer id,

        String name,

        String description,

        List<ProductSimpleResponseDTO> products
) {
}
