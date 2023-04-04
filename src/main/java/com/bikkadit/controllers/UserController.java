package com.bikkadit.controllers;

import com.bikkadit.dtos.*;
import com.bikkadit.config.AppConstant;

import com.bikkadit.services.FileService;
import com.bikkadit.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private FileService fileservice;
    @Value("${user.profile.image.path}")

    private String imageUploadPath;


    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Intiating controller request for createUser");
        UserDto userDto1 = userservice.createUser(userDto);
        userDto1.setIsactive(AppConstant.YES);
        userDto1.setIsactive(AppConstant.NO);
        logger.info("Completed controller request for createUser");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") long userId, @RequestBody UserDto userDto) {
        logger.info("Initating controller request for updateUser :{} ", userId);
        UserDto updateduser = userservice.updateUser(userDto, userId);
        logger.info("Completed controller request for updateUser :{}",userId);
        return new ResponseEntity<>(updateduser, HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable long userId) {
        logger.info("Initiating controller request for deleteUser");

        userservice.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message(AppConstant.USER_DELETE).success(true).status(HttpStatus.OK).build();
        logger.info("Completed controller request for delete");
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                 @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity<>(userservice.getAllUsers(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {
        logger.info("Initating request for get single user");
        UserDto userDto = userservice.getUserById(userId);
        logger.info("Completed request for get single user");
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userservice.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        return new ResponseEntity<>(userservice.searchUser(keyword), HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable long userId) throws IOException {
      logger.info("Initaiting controller request for upload user image");

        String imageName = fileservice.uploadFile(image, imageUploadPath);

        UserDto user = userservice.getUserById(userId);

        user.setImagename(imageName);

        UserDto userDto = userservice.updateUser(user, userId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        logger.info("Completed controller request for upload user image");
       return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }



    //server user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable long userId, HttpServletResponse response) throws IOException {

        UserDto user = userservice.getUserById(userId);
        logger.info("User image name :{}",user.getImagename());
        InputStream resource = fileservice.getResource(imageUploadPath, user.getImagename());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
