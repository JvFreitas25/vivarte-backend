package br.com.vivarte.vivarte.repository;


import br.com.vivarte.vivarte.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends
        JpaRepository<Category, Integer> {

    public Optional<Category> findByName(String name);
}
