package com.bikkadit.services.impl;

import com.bikkadit.config.AppConstant;
import com.bikkadit.dtos.CategoryDto;
import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.UserDto;
import com.bikkadit.exception.ResourceNotFoundException;
import com.bikkadit.helper.Helper;
import com.bikkadit.model.Category;
import com.bikkadit.model.User;
import com.bikkadit.repositories.CategoryRepository;
import com.bikkadit.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryrepo;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        logger.info("Initiating dao request for create category");
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category savecategory = this.categoryrepo.save(category);
        logger.info("Completed dao request for create category");
        return this.modelMapper.map(savecategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long categoryId) {
        logger.info("Intitating dao request for update category :{}", categoryId);
        Category category = categoryrepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));
       //update deatilsi
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatecategory = categoryrepo.save(category);
        logger.info("Completed dao request for update category :{}",categoryId);
        return modelMapper.map(updatecategory,CategoryDto.class);
    }

    @Override
    public void delete(Long categoryId) {
        logger.info("Intiating dao request for delete category");
        Category category = categoryrepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));

        categoryrepo.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pagebale = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> page = categoryrepo.findAll(pagebale);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
        return response;
    }

    @Override
    public CategoryDto getsingleCategory(Long categoryId) {
        Category category = categoryrepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));

        return modelMapper.map(category,CategoryDto.class);
    }


    }

