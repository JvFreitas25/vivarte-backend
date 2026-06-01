package br.com.vivarte.vivarte.dto.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponseDTO(

        Integer id,

        Integer productId,

        String productName,

        BigDecimal unitPrice,

        Integer quantity,

        BigDecimal subtotal
) {
}
