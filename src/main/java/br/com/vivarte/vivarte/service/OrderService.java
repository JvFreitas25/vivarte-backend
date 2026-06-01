package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.Order.OrderRequestDTO;
import br.com.vivarte.vivarte.dto.Order.OrderResponseDTO;
import br.com.vivarte.vivarte.dto.OrderItem.OrderItemResponseDTO;
import br.com.vivarte.vivarte.entity.*;
import br.com.vivarte.vivarte.enums.OrderStatus;
import br.com.vivarte.vivarte.enums.Role;
import br.com.vivarte.vivarte.exception.BadRequestException;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IOrderRepository orderRepository;
    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final IOrderItemRepository orderItemRepository;
    private final IUserRepository userRepository;

    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        Cart cart = cartRepository.findByClientId(request.userId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "Carrinho não encontrado"
                        ));

        if (cart.getItems().isEmpty()) {

            throw new BadRequestException(
                    "Carrinho vazio"
            );
        }

        BigDecimal total =
                cart.getItems()
                        .stream()
                        .map(item ->
                                item.getProduct()
                                        .getPrice()
                                        .multiply(
                                                BigDecimal.valueOf(
                                                        item.getQuantity()
                                                )
                                        )
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        Order order = Order.builder()
                .client(user)
                .shippingAddress(request.shippingAddress())
                .status(OrderStatus.PENDING)
                .totalPrice(total)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        List<OrderItem> orderItems =
                cart.getItems()
                        .stream()
                        .map(cartItem -> {

                            Product product =
                                    cartItem.getProduct();

                            return OrderItem.builder()
                                    .order(order)
                                    .product(product)
                                    .productName(
                                            product.getName()
                                    )
                                    .unitPrice(
                                            product.getPrice()
                                    )
                                    .quantity(
                                            cartItem.getQuantity()
                                    )
                                    .build();
                        })
                        .toList();

        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(cart.getItems());

        return toDto(order, orderItems);
    }

    public List<OrderResponseDTO> findAll() {

        return orderRepository.findAll()
                .stream()
                .map(order -> toDto(
                        order,
                        order.getItems()
                ))
                .toList();
    }

    public OrderResponseDTO findById(Integer id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Pedido não encontrado"
                        ));

        return toDto(order, order.getItems());
    }

    public List<OrderResponseDTO> findByUserId(
            Integer userId
    ) {

        return orderRepository.findAllByClientId(userId)
                .stream()
                .map(order -> toDto(
                        order,
                        order.getItems()
                ))
                .toList();
    }

    public OrderResponseDTO updateStatus(
            Integer orderId,
            OrderStatus status,
            Integer userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        if (user.getRole() != Role.ADMIN) {

            throw new BadRequestException(
                    "Apenas admins podem alterar status"
            );
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Pedido não encontrado"
                        ));

        order.setStatus(status);

        orderRepository.save(order);

        return toDto(order, order.getItems());
    }

    public void delete(Integer id, Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        if (user.getRole() != Role.ADMIN) {

            throw new BadRequestException(
                    "Apenas admins podem deletar pedidos"
            );
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Pedido não encontrado"
                        ));

        orderRepository.delete(order);
    }

    private OrderResponseDTO toDto(
            Order order,
            List<OrderItem> items
    ) {

        List<OrderItemResponseDTO> itemDtos =
                items.stream()
                        .map(this::toItemDto)
                        .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getClient().getId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                itemDtos
        );
    }

    private OrderItemResponseDTO toItemDto(
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
