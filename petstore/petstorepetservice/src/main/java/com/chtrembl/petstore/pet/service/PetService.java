package com.chtrembl.petstore.pet.service;

import com.chtrembl.petstore.pet.entity.PetEntity;
import com.chtrembl.petstore.pet.model.Pet;
import com.chtrembl.petstore.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public List<Pet> findPetsByStatus(List<String> status) {
        log.info("Finding pets with status: {}", status);
        List<PetEntity> entities;
        if (status == null || status.isEmpty()) {
            entities = petRepository.findAll();
        } else {
            entities = petRepository.findByStatusIn(status);
        }
        return entities.stream().map(PetMapper::toModel).collect(Collectors.toList());
    }

    public Optional<Pet> findPetById(Long petId) {
        log.info("Finding pet with id: {}", petId);
        return petRepository.findById(petId).map(PetMapper::toModel);
    }

    public List<Pet> getAllPets() {
        log.info("Getting all pets");
        return petRepository.findAll().stream().map(PetMapper::toModel).collect(Collectors.toList());
    }

    public int getPetCount() {
        return (int) petRepository.count();
    }
}
