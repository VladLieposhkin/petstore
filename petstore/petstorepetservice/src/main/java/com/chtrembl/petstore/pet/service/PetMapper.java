package com.chtrembl.petstore.pet.service;

import com.chtrembl.petstore.pet.entity.*;
import com.chtrembl.petstore.pet.model.*;
import java.util.stream.Collectors;

class PetMapper {
    static Pet toModel(PetEntity e) {
        if (e == null) return null;
        Pet p = new Pet();
        p.setId(e.getId());
        p.setName(e.getName());
        Category c = new Category();
        if (e.getCategory() != null) {
            c.setId(e.getCategory().getId());
            c.setName(e.getCategory().getName());
        }
        p.setCategory(c);
        p.setPhotoURL(e.getPhotoURL());
        p.setStatus(Pet.Status.fromValue(e.getStatus()));
        if (e.getTags() != null) {
            p.setTags(e.getTags().stream().map(t -> {
                Tag mt = new Tag();
                mt.setId(t.getId());
                mt.setName(t.getName());
                return mt;
            }).collect(Collectors.toList()));
        }
        return p;
    }
}