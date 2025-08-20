package com.chtrembl.petstore.order.repository;

import com.chtrembl.petstore.order.model.Order;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CosmosRepository<Order, String> {
}
