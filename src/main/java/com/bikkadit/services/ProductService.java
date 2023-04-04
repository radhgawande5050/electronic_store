package com.bikkadit.services;

import com.bikkadit.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto,Long productId);

    void deleteProduct(Long productId);

    List<ProductDto> getAllProducts();

    ProductDto getsingleproduct(long productId);
}
