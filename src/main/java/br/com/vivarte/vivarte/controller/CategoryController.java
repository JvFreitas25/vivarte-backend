package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.Category.CategoryRequestDTO;
import br.com.vivarte.vivarte.dto.Category.CategoryResponseDTO;
import br.com.vivarte.vivarte.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO create(
            @Valid @RequestBody CategoryRequestDTO request,
            @RequestParam Integer userId
    ) {

        return categoryService.create(
                request,
                userId
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDTO> findAll() {

        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO findById(
            @PathVariable Integer id
    ) {

        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer id,
            @RequestParam Integer userId
    ) {

        categoryService.delete(id, userId);
    }
}

