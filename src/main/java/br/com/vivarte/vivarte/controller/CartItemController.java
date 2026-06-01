package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.CartItem.CartItemResponseDTO;
import br.com.vivarte.vivarte.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService
            cartItemService;

    @PutMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponseDTO updateQuantity(
            @PathVariable Integer cartItemId,
            @RequestParam Integer quantity
    ) {

        return cartItemService.updateQuantity(
                cartItemId,
                quantity
        );
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer cartItemId
    ) {

        cartItemService.delete(cartItemId);
    }
}
