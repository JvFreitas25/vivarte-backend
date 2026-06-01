package br.com.vivarte.vivarte.dto.Product;

import java.math.BigDecimal;

public record ProductSimpleResponseDTO(
        Integer id,
        String name,
        BigDecimal price,
        Integer stock
) {}