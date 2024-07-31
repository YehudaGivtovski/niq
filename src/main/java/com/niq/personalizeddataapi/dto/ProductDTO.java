package com.niq.personalizeddataapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    @NotBlank(message = "Product id is required")
    private String productId;
    @NotBlank(message = "Product category is required")
    private String category;
    @NotBlank(message = "Product brand is required")
    private String brand;

}
