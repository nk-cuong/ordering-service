package com.esoft.orderingservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderRepoTest {

    @Autowired
    private OrderRepo orderRepo;

}
