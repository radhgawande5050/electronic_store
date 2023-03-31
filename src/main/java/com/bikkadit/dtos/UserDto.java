package com.bikkadit.dtos;

import com.bikkadit.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDto extends BaseEntityDto {

    private long userId;
   @Size(min=3,max=20,message = "Invalid name !!")
    private String name;
 @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$",message = "Invalid Email !!")
   @NotBlank(message = "Email is Compulsory !!")
    private String email;
   @NotBlank(message = "password is required !!")
    private String password;
   @Size(min=4,max=6,message="Invalid Gender")
    private String gender;
   @NotBlank(message = "Write Something about yourself !!")
    private String about;
   @ImageNameValid
    private String imagename;
}
