package com.bikkadit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseEntityDto {
    private Long categoryId;
    @NotBlank
    @Min(value=4, message = "title must be of minimum 4 characters !!")
    private String title;

    @NotBlank(message = "Description is Required !!")
    private String Description;

    private String coverImage;
}
