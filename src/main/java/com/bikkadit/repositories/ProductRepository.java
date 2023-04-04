package com.bikkadit.repositories;

import com.bikkadit.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByTitleContaining(String subtitle);

    List<Product>findByLiveTrue();
}
