package br.com.vivarte.vivarte.repository;


import br.com.vivarte.vivarte.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends
        JpaRepository<Order, Integer> {

    List<Order> findAllByClientId(Integer clientId);
}
