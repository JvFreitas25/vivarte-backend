package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.CartItem.CartItemResponseDTO;
import br.com.vivarte.vivarte.entity.CartItem;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.ICartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final ICartItemRepository cartItemRepository;

    public CartItemResponseDTO updateQuantity(
            Integer cartItemId,
            Integer quantity
    ) {

        CartItem item =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Item não encontrado"
                                ));

        item.setQuantity(quantity);

        cartItemRepository.save(item);

        return toDto(item);
    }

    public void delete(Integer cartItemId) {

        CartItem item =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Item não encontrado"
                                ));

        cartItemRepository.delete(item);
    }

    private CartItemResponseDTO toDto(
            CartItem item
    ) {

        BigDecimal subtotal =
                item.getProduct()
                        .getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        );

        return new CartItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                subtotal
        );
    }
}
