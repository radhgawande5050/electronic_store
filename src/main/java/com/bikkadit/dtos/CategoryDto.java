package com.bikkadit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseEntityDto {
    private Long categoryId;
   @Size(min=4, message = "title must be of minimum 4 characters !!")
    private String title;

    @Size(min=30,max=80,message = "Description is Required !!")
    private String description;

    private String coverImage;
}
