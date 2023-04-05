package com.bikkadit.services.impl;

import com.bikkadit.config.AppConstant;
import com.bikkadit.dtos.CategoryDto;
import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.ProductDto;
import com.bikkadit.exception.ResourceNotFoundException;
import com.bikkadit.helper.Helper;
import com.bikkadit.model.Category;
import com.bikkadit.model.Product;
import com.bikkadit.repositories.ProductRepository;
import com.bikkadit.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productrepo;

    @Autowired
    private ModelMapper mapper;

    private Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Inititating dao request for create Product");
        Product product = this.mapper.map(productDto, Product.class);
        Product saveProudct = productrepo.save(product);
        logger.info("Completed dao request for create Product");
        return this.mapper.map(saveProudct,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        logger.info("Initiating dao request for update Product :{}", productId);
        Product product = productrepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product updatedProduct = productrepo.save(product);
        logger.info("Completed dao request for update Product : {}",productId);
        return this.mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId) {
        logger.info("Initiating dao request for delete product");
        Product product = productrepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_DELETE));
       logger.info("Completed dao request for delete product");
        productrepo.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao request for get all Products");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pagebale = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productrepo.findAll(pagebale);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed dao request for get all Products");
        return response;
    }

    @Override
    public ProductDto getsingleproduct(long productId) {
        logger.info("Initating dao request for get single Product");
        Product singleProduct = productrepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));
        logger.info("Completed dao request for gey single product");
        return this.mapper.map(singleProduct,ProductDto.class);
    }
}
