package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.Cart.CartResponseDTO;
import br.com.vivarte.vivarte.dto.CartItem.CartItemResponseDTO;
import br.com.vivarte.vivarte.entity.Cart;
import br.com.vivarte.vivarte.entity.CartItem;
import br.com.vivarte.vivarte.entity.Product;
import br.com.vivarte.vivarte.entity.User;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.ICartItemRepository;
import br.com.vivarte.vivarte.repository.ICartRepository;
import br.com.vivarte.vivarte.repository.IProductRepository;
import br.com.vivarte.vivarte.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ICartRepository cartRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;
    private final ICartItemRepository cartItemRepository;

    private Cart createCart(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        Cart cart = Cart.builder()
                .client(user)
                .createdAt(LocalDateTime.now())
                .build();

        return cartRepository.save(cart);
    }

    public CartResponseDTO getCartByUserId(Integer userId) {

        Cart cart = cartRepository.findByClientId(userId)
                .orElseGet(() -> createCart(userId));

        return toDto(cart);
    }

    public CartResponseDTO addProduct(
            Integer userId,
            Integer productId,
            Integer quantity
    ) {

        Cart cart = cartRepository.findByClientId(userId)
                .orElseGet(() -> createCart(userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Produto não encontrado"
                        ));

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductId(
                        cart.getId(),
                        productId
                );

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            item.setQuantity(
                    item.getQuantity() + quantity
            );

            cartItemRepository.save(item);

        } else {

            CartItem item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();

            cartItemRepository.save(item);
        }

        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow();

        return toDto(updatedCart);
    }

    public CartResponseDTO updateQuantity(
            Integer userId,
            Integer productId,
            Integer quantity
    ) {

        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "A quantidade não pode ser negativa"
            );
        }

        Cart cart = cartRepository.findByClientId(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Carrinho não encontrado"
                        ));

        CartItem item =
                cartItemRepository.findByCartIdAndProductId(
                                cart.getId(),
                                productId
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Item não encontrado"
                                ));

        if (quantity == 0) {

            cartItemRepository.delete(item);

        } else {

            item.setQuantity(quantity);

            cartItemRepository.save(item);
        }

        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow();

        return toDto(updatedCart);
    }

    public CartResponseDTO removeProduct(
            Integer userId,
            Integer productId
    ) {

        Cart cart = cartRepository.findByClientId(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Carrinho não encontrado"
                        ));

        CartItem item =
                cartItemRepository.findByCartIdAndProductId(
                                cart.getId(),
                                productId
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Item não encontrado"
                                ));

        cartItemRepository.delete(item);

        return toDto(cart);
    }

    public void clearCart(Integer userId) {

        Cart cart = cartRepository.findByClientId(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Carrinho não encontrado"
                        ));

        cartItemRepository.deleteAll(cart.getItems());
    }

    public BigDecimal getTotal(Integer userId) {

        Cart cart = cartRepository.findByClientId(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Carrinho não encontrado"
                        ));

        return cart.getItems()
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
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CartResponseDTO toDto(Cart cart) {

        List<CartItemResponseDTO> items =
                cart.getItems()
                        .stream()
                        .map(this::toItemDto)
                        .toList();

        BigDecimal total =
                items.stream()
                        .map(CartItemResponseDTO::subtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponseDTO(
                cart.getId(),
                cart.getClient().getId(),
                items,
                total
        );
    }

    private CartItemResponseDTO toItemDto(
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
