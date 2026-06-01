package br.com.vivarte.vivarte.repository;

import br.com.vivarte.vivarte.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends
        JpaRepository<Cart, Integer> {

    Optional<Cart> findByClientId(Integer clientId);
}
