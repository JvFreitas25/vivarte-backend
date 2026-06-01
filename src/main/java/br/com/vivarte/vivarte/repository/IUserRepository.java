package br.com.vivarte.vivarte.repository;

import br.com.vivarte.vivarte.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends
        JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
