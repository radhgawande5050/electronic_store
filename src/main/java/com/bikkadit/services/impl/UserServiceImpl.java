package com.bikkadit.services.impl;

import com.bikkadit.dtos.PageableResponse;
import com.bikkadit.dtos.UserDto;
import com.bikkadit.config.AppConstant;
import com.bikkadit.exception.ResourceNotFoundException;
import com.bikkadit.helper.Helper;
import com.bikkadit.repositories.UserRepository;
import com.bikkadit.model.User;
import com.bikkadit.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userrepo;

    @Autowired
    private ModelMapper modelMapper;
    @Value("${user.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initaiting dao request for create user");

      //  dto-entity
     User user = dtoToEntity(userDto);
        user.setIsactive(AppConstant.YES);
        user.setIsactive(AppConstant.NO);
        User savedUser = userrepo.save(user);
        //entity-dto
    UserDto newDto = entityToDto(savedUser);

     logger.info("Completed dao request for create user");
        return newDto;
}

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        logger.info("Intiating dao request for update user :{}", userId);
        User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImagename(userDto.getImagename());
        User updatedUser = userrepo.save(user);

        UserDto updateuser = entityToDto(updatedUser);
        logger.info("Completed dao request for update user");
        return updateuser;
    }

    @Override
    public void deleteUser(long userId) {
        logger.info("Initiating dao request for delete user");
        User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        //delete user image
        String fullPath=imagePath+user.getImagename();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(NoSuchFileException ex){
            logger.info("User image not found in folder");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        user.setIsactive(AppConstant.NO);
        logger.info("Completed dao request for delete user");
       userrepo.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Intitating dao request for getAllUsers");
        //Sort sort = Sort.by(sortBy);

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pagebale = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page = userrepo.findAll(pagebale);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        logger.info("Completed dao request for getAll users");
        return response;
    }

    @Override
    public UserDto getUserById(long userId) {
        logger.info("Initiating dao request for getuserById");
        User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        logger.info("Completed dao request for getuserById");
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating dao request for getuserByEmail");
        User user = userrepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with this email !!"));
        logger.info("Completed dao request for getuserByEmail");
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating dao request for searchUser");
        List<User> users = userrepo.findByNameContaining(keyword);
        List<UserDto>dtotoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        logger.info("Completed dao request for searchUser");

        return dtotoList;
    }
    private UserDto entityToDto(User savedUser){
       /* UserDto userDto=UserDto.builder().userId(savedUser.getUserId()).name(savedUser.getName())
                .email(savedUser.getEmail()).password(savedUser.getPassword()).about(savedUser.getAbout())
                .gender(savedUser.getGender()).imagename(savedUser.getImagename()).build();*/
        return modelMapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto){
        /*User user=User.builder().userId(userDto.getUserId()).name(userDto.getName()).email(userDto.getEmail())
                .password(userDto.getPassword()).about(userDto.getGender()).imagename(userDto.getImagename()).build();*/
        return modelMapper.map(userDto,User.class);

    }

}
