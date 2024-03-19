package com.esoft.orderingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esoft.orderingservice.dto.BaseResponse;
import com.esoft.orderingservice.dto.OrderDto;
import com.esoft.orderingservice.dto.OrderRequest;
import com.esoft.orderingservice.service.order.CustomerOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerOrderService orderingService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok()
                .body(orderingService.getAll());
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok()
                .body(orderingService.create(request, userDetails));
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId,
            @RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok()
                .body(orderingService.update(orderId, request, userDetails));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<BaseResponse> deleteOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
        orderingService.delete(orderId, userDetails);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), "Order deleted successfully"));
    }
}
