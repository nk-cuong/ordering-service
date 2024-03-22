package com.esoft.orderingservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.esoft.orderingservice.constant.OrderCategory;
import com.esoft.orderingservice.constant.ServiceName;
import com.esoft.orderingservice.dto.OrderDto;
import com.esoft.orderingservice.dto.OrderStatistic;
import com.esoft.orderingservice.entity.Order;
import com.esoft.orderingservice.entity.Product;
import com.esoft.orderingservice.entity.User;
import com.esoft.orderingservice.repository.OrderRepo;
import com.esoft.orderingservice.service.order.AdminOrderService;

@ExtendWith(MockitoExtension.class)
public class AdminOrderServiceTest {

    @Mock
    OrderRepo orderRepo;

    @InjectMocks
    AdminOrderService adminOrderService;

    static Order order1 = new Order();
    static Order order2 = new Order();

    @BeforeAll
    static void prepare() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setServiceName(ServiceName.PHOTO_EDITING);
        product1.setPrice(1000);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setServiceName(ServiceName.VIDEO_EDITING);
        product2.setPrice(3000);

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("JERRY");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("TOM");

        order1.setId(1L);
        order1.setReference("1");
        order1.setCategory(OrderCategory.LUXURY);
        order1.setProduct(product1);
        order1.setQuantity(2);
        order1.setUser(user1);
        order1.setCreatedDate(LocalDate.of(2024, 1, 1));

        order2.setId(2L);
        order2.setReference("2");
        order2.setCategory(OrderCategory.SUPER_LUXURY);
        order2.setProduct(product2);
        order2.setQuantity(1);
        order2.setUser(user2);
        order1.setCreatedDate(LocalDate.of(2023, 1, 1));
    }

    @Test
    public void orderNotFoundException_getByIdTest() {
        when(orderRepo.findById(5L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> adminOrderService.getById(5L));
        assertEquals(exception.getMessage(), "No order found with id: 5");
    }

    @Test
    public void getByIdTest() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(order1));
        OrderDto orderDto = adminOrderService.getById(1L);
        assertAll(
                () -> assertEquals(orderDto.getId(), 1L),
                () -> assertEquals(orderDto.getReference(), "1"),
                () -> assertEquals(orderDto.getCategory(), OrderCategory.LUXURY),
                () -> assertEquals(orderDto.getServiceName(), ServiceName.PHOTO_EDITING),
                () -> assertEquals(orderDto.getQuantity(), 2L),
                () -> assertEquals(orderDto.getTotalAmount(), 2000L));
    }

    @Test
    public void byUserId_getOrderStatisticTest() {
        when(orderRepo.getOrderStatistic(1L, null, null))
                .thenReturn(List.of(order1));
        OrderStatistic orderStatistic = adminOrderService.getOrderStatistic(1L, null, null);
        assertAll(
                () -> assertEquals(orderStatistic.getTotalOrder(), 1L),
                () -> assertEquals(orderStatistic.getTotalAmount(), 2000L),
                () -> assertEquals(orderStatistic.getOrders().size(), 1));
    }

    @Test
    public void byFromDateToDate_getOrderStatisticTest() {
        when(orderRepo.getOrderStatistic(null, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1)))
                .thenReturn(List.of(order1, order2));
        OrderStatistic orderStatistic = adminOrderService.getOrderStatistic(null, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1));
        assertAll(
                () -> assertEquals(orderStatistic.getTotalOrder(), 2L),
                () -> assertEquals(orderStatistic.getTotalAmount(), 5000L),
                () -> assertEquals(orderStatistic.getOrders().size(), 2));
    }
}
