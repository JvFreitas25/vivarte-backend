package br.com.vivarte.vivarte.dto.Cart;

import br.com.vivarte.vivarte.dto.CartItem.CartItemResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(

        Integer id,

        Integer userId,

        List<CartItemResponseDTO> items,

        BigDecimal totalPrice
) {
}
