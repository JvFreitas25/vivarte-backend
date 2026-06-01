package br.com.vivarte.vivarte.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private Boolean mainImage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
