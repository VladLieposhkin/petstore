package com.chtrembl.petstore.product.service;

import com.chtrembl.petstore.product.entity.ProductEntity;
import com.chtrembl.petstore.product.model.Product;
import com.chtrembl.petstore.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findProductsByStatus(List<String> status) {
        log.info("Finding products with status: {}", status);
        List<ProductEntity> entities = (status == null || status.isEmpty())
                ? productRepository.findAll()
                : productRepository.findByStatusIn(status);

        return entities.stream().map(ProductMapper::toModel).toList();
    }

    public Optional<Product> findProductById(Long productId) {
        log.info("Finding product with id: {}", productId);
        return productRepository.findById(productId).map(ProductMapper::toModel);
    }

    public List<Product> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll().stream()
                .map(ProductMapper::toModel)
                .toList();
    }

    public int getProductCount() {
        return (int) productRepository.count();
    }
}
