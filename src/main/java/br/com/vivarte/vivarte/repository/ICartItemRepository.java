package br.com.vivarte.vivarte.repository;

import br.com.vivarte.vivarte.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends
        JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartIdAndProductId(
            Integer cartId,
            Integer productId
    );

    List<CartItem> findAllByCartId(Integer cartId);
}
