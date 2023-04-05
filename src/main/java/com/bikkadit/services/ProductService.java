package com.bikkadit.services;

import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto,Long productId);

    void deleteProduct(Long productId);

    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    ProductDto getsingleproduct(long productId);
}
