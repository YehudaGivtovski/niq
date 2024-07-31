package com.niq.personalizeddataapi.controller.external;

import com.niq.personalizeddataapi.dto.FilterProductDTO;
import com.niq.personalizeddataapi.dto.ProductDTO;
import com.niq.personalizeddataapi.exception.ApplicationException;
import com.niq.personalizeddataapi.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@NotNull @Valid @RequestBody final ProductDTO productDTO) throws ApplicationException {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@NotBlank @PathVariable final String id) throws ApplicationException {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@NotBlank @PathVariable final String id) throws ApplicationException {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/get-product-by-shopper")
    public ResponseEntity<List<ProductDTO>> getProductsByShopper(@Valid @NotNull @RequestBody final FilterProductDTO filterProductDTO) {
        return ResponseEntity.ok(productService.getProductsByShopper(filterProductDTO));
    }
}
