package com.example.demo.repositories;

import com.example.demo.models.OrderElement;
import org.springframework.data.repository.CrudRepository;

public interface OrderElementRepository extends CrudRepository<OrderElement, Long> {

}
