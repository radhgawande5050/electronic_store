package com.bikkadit.dtos;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class BaseEntityDto {

    private LocalDate createdate;

    private LocalDate updatedate;

    private String isactive;
}
