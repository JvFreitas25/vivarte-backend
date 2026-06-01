package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.OrderItem.OrderItemResponseDTO;
import br.com.vivarte.vivarte.entity.OrderItem;
import br.com.vivarte.vivarte.repository.IOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final IOrderItemRepository orderItemRepository;

    public List<OrderItemResponseDTO> findByOrderId(
            Integer orderId
    ) {

        return orderItemRepository
                .findAllByOrderId(orderId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private OrderItemResponseDTO toDto(
            OrderItem item
    ) {

        BigDecimal subtotal =
                item.getUnitPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        );

        return new OrderItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity(),
                subtotal
        );
    }
}
