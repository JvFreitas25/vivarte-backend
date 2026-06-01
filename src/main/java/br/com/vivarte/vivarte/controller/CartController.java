package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.Cart.CartResponseDTO;
import br.com.vivarte.vivarte.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDTO getCart(
            @PathVariable Integer userId
    ) {

        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDTO addProduct(
            @PathVariable Integer userId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity
    ) {

        return cartService.addProduct(
                userId,
                productId,
                quantity
        );
    }

    @PatchMapping("/{userId}/items/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDTO updateQuantity(
            @PathVariable Integer userId,
            @PathVariable Integer productId,
            @RequestParam Integer quantity
    ) {

        return cartService.updateQuantity(
                userId,
                productId,
                quantity
        );
    }

    @DeleteMapping("/{userId}/remove")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDTO removeProduct(
            @PathVariable Integer userId,
            @RequestParam Integer productId
    ) {

        return cartService.removeProduct(
                userId,
                productId
        );
    }

    @DeleteMapping("/{userId}/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(
            @PathVariable Integer userId
    ) {

        cartService.clearCart(userId);
    }
}
