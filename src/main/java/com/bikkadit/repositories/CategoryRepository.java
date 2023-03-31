package com.bikkadit.repositories;

import com.bikkadit.model.Category;
import com.bikkadit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
