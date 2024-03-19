package com.esoft.orderingservice.service.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esoft.orderingservice.dto.OrderDto;
import com.esoft.orderingservice.dto.OrderStatistic;
import com.esoft.orderingservice.repository.OrderRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepo orderRepo;

    @Transactional(readOnly = true)
    public OrderDto getById(Long orderId) {
        return orderRepo.findById(orderId)
                .map(OrderDto::from)
                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
    }

    @Transactional(readOnly = true)
    public OrderStatistic getOrderStatistic(Long userId, LocalDate fromDate, LocalDate toDate) {

        List<OrderDto> orders = orderRepo.getOrderStatistic(userId, fromDate, toDate)
                .stream()
                .map(OrderDto::from)
                .toList();
        return OrderStatistic.builder()
                .totalOrder((long) orders.size())
                .totalAmount(orders.stream().mapToLong(OrderDto::getTotalAmount).sum())
                .orders(orders)
                .build();
    }
}
