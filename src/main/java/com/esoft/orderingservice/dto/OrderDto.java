package com.esoft.orderingservice.dto;

import java.time.LocalDate;

import com.esoft.orderingservice.constant.OrderCategory;
import com.esoft.orderingservice.constant.ServiceName;
import com.esoft.orderingservice.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String reference;
    private OrderCategory category;
    private ServiceName serviceName;
    private long quantity;
    private long totalAmount;
    private long userId;
    private LocalDate createdDate;

    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getId(),
                order.getReference(),
                order.getCategory(),
                order.getProduct().getServiceName(),
                order.getQuantity(),
                order.getTotalAmount(),
                order.getUser().getId(),
                order.getCreatedDate());
    }
}
