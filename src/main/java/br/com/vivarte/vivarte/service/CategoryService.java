package br.com.vivarte.vivarte.service;

import br.com.vivarte.vivarte.dto.Category.CategoryRequestDTO;
import br.com.vivarte.vivarte.dto.Category.CategoryResponseDTO;
import br.com.vivarte.vivarte.dto.Product.ProductSimpleResponseDTO;
import br.com.vivarte.vivarte.entity.Category;
import br.com.vivarte.vivarte.entity.User;
import br.com.vivarte.vivarte.enums.Role;
import br.com.vivarte.vivarte.exception.BadRequestException;
import br.com.vivarte.vivarte.exception.NotFoundException;
import br.com.vivarte.vivarte.repository.ICategoryRepository;
import br.com.vivarte.vivarte.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    private final IUserRepository userRepository;

    public CategoryResponseDTO create(
            CategoryRequestDTO request,
            Integer userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        if (user.getRole() != Role.ADMIN) {

            throw new BadRequestException(
                    "Apenas admins podem criar categorias"
            );
        }

        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();

        categoryRepository.save(category);

        return toDto(category);
    }

    public List<CategoryResponseDTO> findAll() {

        return categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryResponseDTO findById(Integer id) {

        return categoryRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Categoria não encontrada"
                        ));
    }

    public void delete(Integer id, Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Usuário não encontrado"
                        ));

        if (user.getRole() != Role.ADMIN) {

            throw new BadRequestException(
                    "Apenas admins podem deletar categorias"
            );
        }

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Categoria não encontrada"
                                ));

        categoryRepository.delete(category);
    }

    private CategoryResponseDTO toDto(Category category) {

        List<ProductSimpleResponseDTO> products =
                category.getProducts() == null
                        ? List.of()
                        : category.getProducts()
                        .stream()
                        .map(product -> new ProductSimpleResponseDTO(
                                product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getStock()
                        ))
                        .toList();

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                products
        );
    }

}
