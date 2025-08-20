package com.chtrembl.petstore.product.service;

import com.chtrembl.petstore.product.entity.*;
import com.chtrembl.petstore.product.model.*;

import java.util.List;

public class ProductMapper {
    public static Product toModel(ProductEntity entity) {
        if (entity == null) return null;

        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .photoURL(entity.getPhotoURL())
                .status(Product.Status.fromValue(entity.getStatus()))
                .category(new Category(entity.getCategory().getId(), entity.getCategory().getName()))
                .tags(entity.getTags().stream()
                        .map(tag -> new Tag(tag.getId(), tag.getName()))
                        .toList())
                .build();
    }

    public static ProductEntity toEntity(Product model, CategoryEntity category, List<TagEntity> tags) {
        if (model == null) return null;

        return ProductEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .photoURL(model.getPhotoURL())
                .status(model.getStatus().getValue())
                .category(category)
                .tags(tags)
                .build();
    }
}
