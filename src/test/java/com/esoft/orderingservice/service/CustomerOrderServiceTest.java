package com.esoft.orderingservice.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.esoft.orderingservice.dto.OrderDto;
import com.esoft.orderingservice.repository.OrderRepo;
import com.esoft.orderingservice.service.order.CustomerOrderService;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private CustomerOrderService customerOrderService;

    @Test
    void testGetAll() {
        when(orderRepo.findByUserUsername("jerry"))
                .thenReturn(List.of());
        List<OrderDto> orderDtos = customerOrderService.getAll("jerry");
        assertTrue(orderDtos.isEmpty());
    }
}
