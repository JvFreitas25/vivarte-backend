package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.Product.ProductRequestDTO;
import br.com.vivarte.vivarte.dto.Product.ProductResponseDTO;
import br.com.vivarte.vivarte.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(
            @Valid @RequestBody ProductRequestDTO request,
            @RequestParam Integer userId
    ) {

        return productService.create(
                request,
                userId
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> findAll() {

        return productService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO findById(
            @PathVariable Integer id
    ) {

        return productService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer id,
            @RequestParam Integer userId
    ) {

        productService.delete(id, userId);
    }
}

