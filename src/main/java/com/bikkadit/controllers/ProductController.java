package com.bikkadit.controllers;

import com.bikkadit.config.AppConstant;
import com.bikkadit.dtos.ApiResponseMessage;
import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.ProductDto;
import com.bikkadit.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Products")
public class ProductController {

    @Autowired
    private ProductService productservice;

    private Logger logger= LoggerFactory.getLogger(ProductController.class);
    @PostMapping("/create")
    public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto productDto){
        logger.info("Initiating controller request for create product");
        ProductDto product = productservice.createProduct(productDto);
        logger.info("Completed controller request for creat product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable long productId, @RequestBody ProductDto productDto){
        logger.info("Initiating controller request for update product: {}",productId);
        ProductDto productDto1 = productservice.updateProduct(productDto, productId);
        logger.info("Completed controller request for update product : {}",productId);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);

    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage>deleteProduct(@PathVariable long productId){
        logger.info("Initiating controller request for delete product");
        productservice.deleteProduct(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(AppConstant.PRODUCT_DELETE).status(HttpStatus.OK).success(true).build();
        logger.info("Completed controller request for delete product");
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<ProductDto>>getAllProduct(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                     @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
       logger.info("Initiating controller request for get all products ");
        PageableResponse<ProductDto> pageableResponse = productservice.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed controller request for get all products");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto>getSingleProduct(@PathVariable long productId){
        logger.info("Initiating controller request for get single product");
        ProductDto getsingleproduct = productservice.getsingleproduct(productId);
        logger.info("Completed controller request for get single product");
        return new ResponseEntity<>(getsingleproduct,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
                                                                  @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                  @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                  @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
       System.out.println("Initiating controller request for get AllLive ");
        PageableResponse<ProductDto> allLive = productservice.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        System.out.println("Completed controller request for get AllLive");
        return new ResponseEntity<>(allLive,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<PageableResponse<ProductDto>>searchByTitle(@PathVariable String subTitle,
                                                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                     @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        System.out.println("Initiating controller request for Search ByTitle");
        PageableResponse<ProductDto> searchByTitle = productservice.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        System.out.println("Completed cotroller request for Search ByTitle");
        return new ResponseEntity<>(searchByTitle,HttpStatus.OK);
    }
}
