package com.chtrembl.petstore.product.repository;

import com.chtrembl.petstore.product.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {}
