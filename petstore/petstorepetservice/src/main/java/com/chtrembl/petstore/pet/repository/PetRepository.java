package com.chtrembl.petstore.pet.repository;

import com.chtrembl.petstore.pet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
    java.util.List<PetEntity> findByStatus(String status);
    java.util.List<PetEntity> findByStatusIn(java.util.Collection<String> statuses);
    long count();
}