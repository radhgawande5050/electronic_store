package com.bikkadit.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public class BaseEntity {
    @Column(name="create_table",updatable = false)
    @CreationTimestamp
    private LocalDate createdate;
    @Column(name="update_table",updatable = false)
    @CreationTimestamp
    private LocalDate updatedate;
    @Column(name="isActive_switch")
    private String isactive;

}
