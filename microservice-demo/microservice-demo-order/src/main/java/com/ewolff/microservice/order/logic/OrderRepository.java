package com.ewolff.microservice.order.logic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "orderdata", path = "orderdata")
public interface OrderRepository extends JpaRepository<Order, Long> {

}
