package br.com.vivarte.vivarte.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    private BigDecimal unitPrice;

    private Integer quantity;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
}
