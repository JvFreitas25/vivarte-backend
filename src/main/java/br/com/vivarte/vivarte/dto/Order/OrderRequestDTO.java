package br.com.vivarte.vivarte.dto.Order;

public record OrderRequestDTO(

        Integer userId,

        String shippingAddress
) {
}
