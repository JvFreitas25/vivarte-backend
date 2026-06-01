package br.com.vivarte.vivarte.repository;


import br.com.vivarte.vivarte.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends
        JpaRepository<Product, Integer> {

    List<Product> findAllByCategoryId(
            Long categoryId
    );

    List<Product> findByNameContainingIgnoreCase(
            String name
    );
}
