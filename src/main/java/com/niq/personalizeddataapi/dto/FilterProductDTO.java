package com.niq.personalizeddataapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilterProductDTO {

    @NotNull
    private String shopperId;
    private String category;
    private String brand;
    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 100, message = "Limit must be at most 100")
    private Integer limit = 10;

}
