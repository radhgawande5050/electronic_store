package com.bikkadit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long productId;
    private String title;
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer quantity;
    @CreationTimestamp
    private Date addedDate;
    private boolean live;
    private boolean stock;
}
