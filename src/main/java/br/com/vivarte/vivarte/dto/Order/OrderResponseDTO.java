package br.com.vivarte.vivarte.dto.Order;

import br.com.vivarte.vivarte.dto.OrderItem.OrderItemResponseDTO;
import br.com.vivarte.vivarte.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(

        Integer id,

        Integer userId,

        OrderStatus status,

        BigDecimal totalPrice,

        LocalDateTime createdAt,

        List<OrderItemResponseDTO> items
) {
}
