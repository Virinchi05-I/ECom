package com.ECom.ecommerce.services.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.product.request.CreateProductRequest;
import com.ECom.ecommerce.dtos.product.request.UpdateProductRequest;
import com.ECom.ecommerce.dtos.product.response.ProductListResponse;
import com.ECom.ecommerce.dtos.product.response.ProductResponse;
import com.ECom.ecommerce.entities.Brand;
import com.ECom.ecommerce.entities.Category;
import com.ECom.ecommerce.entities.ItemType;
import com.ECom.ecommerce.entities.Product;
import com.ECom.ecommerce.repositories.BrandRepo;
import com.ECom.ecommerce.repositories.CategoryRepo;
import com.ECom.ecommerce.repositories.ItemTypeRepo;
import com.ECom.ecommerce.repositories.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Autowired
    private BrandRepo brandRepo;
    
    @Autowired
    private ItemTypeRepo itemTypeRepo;

    

    @Override
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setBrand(brandRepo.findById(createProductRequest.getBrandId()).orElse(null));
        product.setCategory(categoryRepo.findById(createProductRequest.getCategoryId()).orElse(null));
        product.setItemType(itemTypeRepo.findById(createProductRequest.getItemTypeId()).orElse(null));
        product.setSpecification(createProductRequest.getSpecification());
        product.setDescription(createProductRequest.getDescription());
        product.setPrice(createProductRequest.getPrice());
        
        Product savedProduct = productRepo.save(product);

        return mapProductToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        
         Product product = productRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (updateProductRequest.getName() != null) product.setName(updateProductRequest.getName());
        if (updateProductRequest.getDescription() != null) product.setDescription(updateProductRequest.getDescription());
        if (updateProductRequest.getSpecification() != null) product.setSpecification(updateProductRequest.getSpecification());
        if (updateProductRequest.getPrice() != null) product.setPrice(updateProductRequest.getPrice());

        if (updateProductRequest.getBrandId() != null) {
            Brand brand = brandRepo.findById(updateProductRequest.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
            product.setBrand(brand);
        }

        if (updateProductRequest.getCategoryId() != null) {
        Category category = categoryRepo.findById(updateProductRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategory(category);
    }

    if (updateProductRequest.getItemTypeId() != null) {
        ItemType itemType = itemTypeRepo.findById(updateProductRequest.getItemTypeId())
                .orElseThrow(() -> new IllegalArgumentException("ItemType not found"));
        product.setItemType(itemType);
    }
        
        Product savedProduct = productRepo.save(product);

        return mapProductToResponse(savedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        
        Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productRepo.delete(product);
    }

    @Override
    public Optional<ProductResponse> findProductById(Long productId) {
        Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return Optional.of(mapProductToResponse(product));
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepo.findAll();

        return products.stream().map(this::mapProductToResponse).toList();
    }

    @Override
    public List<ProductListResponse> findByCategory(Long categoryId) {
        
        Category category = categoryRepo.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        List<Product> products = productRepo.findByCategory(category);
        
        return products.stream().map(this::mapToProductListResponse).toList();
    }

    @Override
    public List<ProductListResponse> findByBrand(Long brandId) {
        
        Brand category = brandRepo.findById(brandId)
            .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        List<Product> products = productRepo.findByBrand(category);
        
        return products.stream().map(this::mapToProductListResponse).toList();
    }

    @Override
    public List<ProductListResponse> findByItemType(Long itemTypeId) {
        
        ItemType itemType = itemTypeRepo.findById(itemTypeId)
            .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        List<Product> products = productRepo.findByItemType(itemType);
        
        return products.stream().map(this::mapToProductListResponse).toList();
    }

   
    @Override
    public List<ProductListResponse> findByName(String name) {
        List<Product> products = productRepo.findByName(name);

        return products.stream().map(this::mapToProductListResponse).toList();
    }

    @Override
    public List<ProductListResponse> findByCategoryAndBrand(Long categoryId, Long brandId) {
       
        Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Brand brand = brandRepo.findById(brandId)
                    .orElseThrow(() -> new IllegalArgumentException("brand not found"));
       
        List<Product> products = productRepo.findByBrandAndCategory(brand, category);

        return products.stream().map(this::mapToProductListResponse).toList();
    }

    @Override
    public List<ProductListResponse> findByCategoryAndBrandAndPrice(Long categoryId, Long brandId, BigDecimal price) {

        Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Brand brand = brandRepo.findById(brandId)
                    .orElseThrow(() -> new IllegalArgumentException("brand not found"));
       
        List<Product> products = productRepo.findByCategoryAndBrandAndPrice(category, brand, price);

        return products.stream().map(this::mapToProductListResponse).toList();
    }

    @Override
    public List<ProductListResponse> searchProducts(Long categoryId, Long brandId, BigDecimal price, Long itemTypeId) {
        List<Product> products;

        Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Brand brand = brandRepo.findById(brandId)
                    .orElseThrow(() -> new IllegalArgumentException("brand not found"));
        
        ItemType itemType = itemTypeRepo.findById(itemTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("itemType not found"));
        

        if (category != null && brand != null && itemType != null) {
        products = productRepo.findByBrandAndCategoryAndItemType(brand, category, itemType);
    } else if (category != null && brand != null) {
        products = productRepo.findByBrandAndCategory(brand, category);
    } else if (category != null && price != null) {
        products = productRepo.findByCategoryAndPrice(category, price);
    } else if (brand != null && itemType != null) {
        products = productRepo.findByBrandAndItemType(brand, itemType);
    } else if (category != null && itemType != null) {
        products = productRepo.findByCategoryAndItemType(category, itemType);
    }else{
        products = productRepo.findAll();
    }

        return products.stream().map(this::mapToProductListResponse).toList();
    }
    
    private ProductResponse mapProductToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setBrandId(product.getBrand().getBrandId());
        response.setCategoryId(product.getCategory().getCategoryId());
        response.setItemType(product.getItemType().getItemTypeId());
        response.setSpecification(product.getSpecification());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        
        return response;
    }

    private ProductListResponse mapToProductListResponse(Product product) {
    ProductListResponse response = new ProductListResponse();
    response.setProducts(List.of(mapProductToResponse(product))); // one product wrapped
    response.setImages(product.getImages());
    response.setCartItems(product.getCartItems());
    response.setOrderItems(product.getOrderItems());
    response.setReviews(product.getReviews());
    response.setProductVariants(product.getProductVariant());
    return response;
    }

    

}