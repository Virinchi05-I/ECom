package com.ECom.ecommerce.dtos.brand.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest{

    private Long brandId;
    private String brandName;      // brand name
    private String logoUrl;     // optional, if seller can upload logo
}

