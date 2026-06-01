package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.ProductImage.ProductImageRequestDTO;
import br.com.vivarte.vivarte.dto.ProductImage.ProductImageResponseDTO;
import br.com.vivarte.vivarte.entity.Product;
import br.com.vivarte.vivarte.entity.ProductImage;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.IProductImageRepository;
import br.com.vivarte.vivarte.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final IProductImageRepository
            productImageRepository;

    private final IProductRepository productRepository;

    public ProductImageResponseDTO create(
            ProductImageRequestDTO request
    ) {

        Product product =
                productRepository.findById(
                                request.productId()
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Produto não encontrado"
                                ));

        ProductImage image = ProductImage.builder()
                .imageUrl(request.imageUrl())
                .mainImage(request.mainImage())
                .product(product)
                .build();

        productImageRepository.save(image);

        return toDto(image);
    }

    public List<ProductImageResponseDTO>
    findByProductId(Integer productId) {

        return productImageRepository
                .findAllByProductId(productId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public void delete(Integer id) {

        ProductImage image =
                productImageRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Imagem não encontrada"
                                ));

        productImageRepository.delete(image);
    }

    private ProductImageResponseDTO toDto(
            ProductImage image
    ) {

        return new ProductImageResponseDTO(
                image.getId(),
                image.getImageUrl(),
                image.getMainImage()
        );
    }
}
