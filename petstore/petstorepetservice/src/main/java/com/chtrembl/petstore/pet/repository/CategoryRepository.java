package com.chtrembl.petstore.pet.repository;

import com.chtrembl.petstore.pet.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}