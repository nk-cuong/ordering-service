package com.esoft.orderingservice.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esoft.orderingservice.dto.OrderDto;
import com.esoft.orderingservice.dto.OrderStatistic;
import com.esoft.orderingservice.service.order.AdminOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(adminOrderService.getById(orderId));
    }

    @GetMapping("/orders/statistics")
    public ResponseEntity<OrderStatistic> getOrderStatistic(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate) {
        return ResponseEntity.ok(adminOrderService.getOrderStatistic(userId, fromDate, toDate));
    }
}
