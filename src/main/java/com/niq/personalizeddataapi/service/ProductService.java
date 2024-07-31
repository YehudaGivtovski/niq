package com.niq.personalizeddataapi.service;

import com.niq.personalizeddataapi.dto.FilterProductDTO;
import com.niq.personalizeddataapi.dto.ProductDTO;
import com.niq.personalizeddataapi.entity.ProductEntity;
import com.niq.personalizeddataapi.exception.ApplicationException;
import com.niq.personalizeddataapi.mapper.ProductMapper;
import com.niq.personalizeddataapi.repository.ProductRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private static final String PRODUCT_NOT_FOUND = "Product not found";

    public ProductDTO saveProduct(@NotNull final ProductDTO productDTO) {
        log.debug("Creating product: {}", productDTO);
        final ProductEntity productEntity = productRepository.save(ProductMapper.INSTANCE.toProductEntity(productDTO));
        return ProductMapper.INSTANCE.toProductDTO(productEntity);
    }

    public void deleteProductById(@NotBlank final String id) throws ApplicationException {
        // validate if product exists before deleting
        productRepository.findById(id).orElseThrow(() -> new ApplicationException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        log.debug("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

    public ProductDTO getProductById(@NotBlank final String id) throws ApplicationException {
        final ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        log.debug("Getting product by id: {}", id);
        return ProductMapper.INSTANCE.toProductDTO(productEntity);
    }

    @Cacheable(value = "products", key = "#root.methodName")
    public List<ProductDTO> getAllProducts() {
        log.debug("Getting all products");
        return productRepository.findAll().stream()
                .map(ProductMapper.INSTANCE::toProductDTO)
                .toList();
    }

    // By caching the results of expensive method calls (such as database queries, computations, or external service calls)
// you can return results much faster for subsequent requests with the same parameters.
    @Cacheable(value = "products", key = "#filterProductDTO.shopperId + '-' + #filterProductDTO.category + '-' + #filterProductDTO.brand + '-' + #filterProductDTO.limit")
// cache key -> For example, if shopperId = "S-1000", category = "Babies", brand = "Babyom", limit = 10, the cache key might be "S-1000-Babies-Babyom-10".
    public List<ProductDTO> getProductsByShopper(@NotNull final FilterProductDTO filterProductDTO) {
        log.debug("Getting products by shopper: {}", filterProductDTO);

        // currently, there is limitation of 100 results per query, need to implement pagination
        final List<ProductEntity> productEntities = productRepository.findProductsByShopperIdAndFilters(
                filterProductDTO.getShopperId(),
                filterProductDTO.getCategory(),
                filterProductDTO.getBrand(),
                filterProductDTO.getLimit()

        );
        return productEntities.stream()
                .map(ProductMapper.INSTANCE::toProductDTO)
                .toList();
    }
}
