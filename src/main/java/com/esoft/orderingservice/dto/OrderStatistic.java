package com.esoft.orderingservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatistic {

    private Long totalOrder;
    private Long totalAmount;
    private List<OrderDto> orders;
    
}
