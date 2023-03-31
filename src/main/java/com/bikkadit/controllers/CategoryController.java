package com.bikkadit.controllers;

import com.bikkadit.config.AppConstant;
import com.bikkadit.dtos.ApiResponseMessage;
import com.bikkadit.dtos.CategoryDto;
import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.services.CategoryService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @PostMapping("/create")
    public ResponseEntity<CategoryDto>createCategory( @Valid @RequestBody CategoryDto categoryDto){
        logger.info("Inititating request for create Cateogry");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        categoryDto1.setIsactive(AppConstant.YES);
        logger.info("Completed controller request for createCategory");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid @PathVariable long categoryId,@RequestBody CategoryDto categoryDto){
        logger.info("Intitating request for createCateogry :{} ", categoryId);
        CategoryDto  updateCategory = categoryService.update(categoryDto, categoryId);
        logger.info("Completed controller request for createCategory");
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<ApiResponseMessage>deleteCategory(@PathVariable long categoryId){
        logger.info("Initating request for delete category ");
         categoryService.delete(categoryId);
         ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category Delete successfully !!").status(HttpStatus.OK).success(true).build();
       logger.info("Completed request for delete category");
       return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>>getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        logger.info("Initiating request for getall category");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for getall category");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>getsingleCategory(@PathVariable long categoryId){
        logger.info("Initating request for get single category");
        CategoryDto categoryDto = categoryService.getsingleCategory(categoryId);
        logger.info("Completed request for get single category");
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
}
