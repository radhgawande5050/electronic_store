package com.bikkadit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    private String title;
    @Column(length = 10000)
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer quantity;
    @CreationTimestamp
    private Date addedDate;
    private boolean live;
    private boolean stock;
}
