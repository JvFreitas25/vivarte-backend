package br.com.vivarte.vivarte.controller;

import br.com.vivarte.vivarte.dto.ProductImage.ProductImageRequestDTO;
import br.com.vivarte.vivarte.dto.ProductImage.ProductImageResponseDTO;
import br.com.vivarte.vivarte.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService
            productImageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductImageResponseDTO create(
            @Valid @RequestBody
            ProductImageRequestDTO request
    ) {

        return productImageService.create(request);
    }

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductImageResponseDTO>
    findByProductId(
            @PathVariable Integer productId
    ) {

        return productImageService
                .findByProductId(productId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer id
    ) {

        productImageService.delete(id);
    }
}
