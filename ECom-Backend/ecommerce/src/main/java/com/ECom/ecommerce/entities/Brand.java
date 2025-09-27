package com.ECom.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long brandId;

    @Column(length = 100, nullable = false)
    private String brandName;
    
    @Column(length = 1000, nullable = false)
    private String brandDescription;

    @Column(length = 1000, nullable = false)
    private String logoUrl;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
