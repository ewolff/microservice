package com.ewolff.microservice.order.logic;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
