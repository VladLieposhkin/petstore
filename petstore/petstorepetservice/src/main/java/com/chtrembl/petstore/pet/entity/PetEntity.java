package com.chtrembl.petstore.pet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "pet")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "photoURL", nullable = false, length = 255)
    private String photoURL;

    @Column(nullable = false, length = 64)
    private String status;

    @ManyToMany
    @JoinTable(
        name = "pet_tag",
        joinColumns = @JoinColumn(name = "pet_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags = new HashSet<>();
}