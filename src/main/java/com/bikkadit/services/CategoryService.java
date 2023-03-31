package com.bikkadit.services;

import com.bikkadit.dtos.CategoryDto;
import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.UserDto;

import java.util.List;

public interface CategoryService {

   CategoryDto create(CategoryDto categoryDto);

   CategoryDto update(CategoryDto categoryDto,Long categoryId);

   void delete(Long categoryId);

   PageableResponse<CategoryDto>getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

   CategoryDto getsingleCategory(Long categoryId);

   //List<CategoryDto> searchUser(String keyword);
}
