package br.com.vivarte.vivarte.dto.CartItem;

import java.math.BigDecimal;

public record CartItemResponseDTO(

        Integer id,

        Integer productId,

        String productName,

        BigDecimal unitPrice,

        Integer quantity,

        BigDecimal subtotal
) {
}
