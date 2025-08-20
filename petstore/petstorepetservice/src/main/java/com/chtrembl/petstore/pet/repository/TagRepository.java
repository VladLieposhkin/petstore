package com.chtrembl.petstore.pet.repository;

import com.chtrembl.petstore.pet.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    boolean existsByName(String name);
}