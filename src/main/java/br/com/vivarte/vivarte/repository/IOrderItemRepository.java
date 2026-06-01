package br.com.vivarte.vivarte.repository;


import br.com.vivarte.vivarte.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends
        JpaRepository<OrderItem, Integer> {

    List<OrderItem> findAllByOrderId(Integer orderId);
}
