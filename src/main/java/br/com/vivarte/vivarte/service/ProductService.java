package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.Category.CategorySimpleResponseDTO;
import br.com.vivarte.vivarte.dto.Product.ProductRequestDTO;
import br.com.vivarte.vivarte.dto.Product.ProductResponseDTO;
import br.com.vivarte.vivarte.dto.ProductImage.ProductImageResponseDTO;
import br.com.vivarte.vivarte.entity.Category;
import br.com.vivarte.vivarte.entity.Product;
import br.com.vivarte.vivarte.entity.User;
import br.com.vivarte.vivarte.enums.Role;
import br.com.vivarte.vivarte.exception.BadRequestException;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.ICategoryRepository;
import br.com.vivarte.vivarte.repository.IProductRepository;
import br.com.vivarte.vivarte.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IUserRepository userRepository;

    public ProductResponseDTO create(
            ProductRequestDTO request,
            Integer userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        if (user.getRole() != Role.ADMIN) {

            throw new BadRequestException(
                    "Apenas admins podem criar produtos"
            );
        }

        Category category =
                categoryRepository.findById(
                                request.categoryId()
                        )
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Categoria não encontrada"
                                ));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();

        category.getProducts().add(product);

        productRepository.save(product);

        return toDto(product);
    }

    public List<ProductResponseDTO> findAll() {

        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ProductResponseDTO findById(Integer id) {

        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Produto não encontrado"
                        ));
    }

    public List<ProductResponseDTO> searchByName(String name) {

        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public void delete(Integer id, Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        if (user.getRole() != Role.ADMIN) {

            throw new BadRequestException(
                    "Apenas admins podem deletar produtos"
            );
        }

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Produto não encontrado"
                                ));

        productRepository.delete(product);
    }

    private ProductResponseDTO toDto(
            Product product
    ) {

        List<ProductImageResponseDTO> images =
                product.getImages() == null
                        ? List.of()
                        : product.getImages()
                        .stream()
                        .map(image -> new ProductImageResponseDTO(
                                image.getId(),
                                image.getImageUrl(),
                                image.getMainImage()
                        ))
                        .toList();

        CategorySimpleResponseDTO category =
                new CategorySimpleResponseDTO (
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getDescription()
                );

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                category,
                images,
                product.getCreatedAt()
        );
    }

}
