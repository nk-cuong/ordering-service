package com.esoft.orderingservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esoft.orderingservice.constant.ServiceName;
import com.esoft.orderingservice.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findByServiceName(ServiceName serviceName);
}
