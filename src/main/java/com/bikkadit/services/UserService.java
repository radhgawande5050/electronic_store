package com.bikkadit.services;

import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,long userId);

    void deleteUser(long userId);

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUserById(long userId);

    UserDto getUserByEmail(String email);

    List<UserDto>searchUser(String keyword);

}
