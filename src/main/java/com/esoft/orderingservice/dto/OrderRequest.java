package com.esoft.orderingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Reference is required")
    private String reference;

    @Pattern(regexp = "^(LUXURY|SUPER_LUXURY|SUPREME_LUXURY)$", message = "Category must be LUXURY, SUPER_LUXURY or SUPREME_LUXURY")
    private String category;
    
    @Pattern(regexp = "^(PHOTO_EDITING|VIDEO_EDITING)$", message = "Service name must be PHOTO_EDITING or VIDEO_EDITING")
    private String serviceName;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}
