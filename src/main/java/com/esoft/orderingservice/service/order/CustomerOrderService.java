package com.esoft.orderingservice.service.order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esoft.orderingservice.constant.OrderCategory;
import com.esoft.orderingservice.constant.ServiceName;
import com.esoft.orderingservice.dto.OrderDto;
import com.esoft.orderingservice.dto.OrderRequest;
import com.esoft.orderingservice.entity.Order;
import com.esoft.orderingservice.entity.Product;
import com.esoft.orderingservice.entity.User;
import com.esoft.orderingservice.repository.OrderRepo;
import com.esoft.orderingservice.repository.ProductRepo;
import com.esoft.orderingservice.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo serviceRepo;

    @Transactional(readOnly = true)
    public List<OrderDto> getAll() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepo.findByUserUsername(userDetails.getUsername())
                .stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto getById(Long orderId, UserDetails userDetails) {
        return orderRepo.findByIdAndUserUsername(orderId, userDetails.getUsername())
                .map(OrderDto::from)
                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
    }

    @Transactional
    public OrderDto create(OrderRequest request, UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No user found with username: " + userDetails.getUsername()));
        Product product = serviceRepo.findByServiceName(ServiceName.valueOf(request.getServiceName()))
                .orElseThrow(
                        () -> new IllegalArgumentException("No service found with name: " + request.getServiceName()));

        Order order = new Order();
        order.setUser(user);
        order.setReference(request.getReference());
        order.setCategory(OrderCategory.valueOf(request.getCategory()));
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        order.setCreatedDate(LocalDate.now());
        return OrderDto.from(orderRepo.save(order));
    }

    @Transactional
    public OrderDto update(Long orderId, OrderRequest request, UserDetails userDetails) {
        Order order = orderRepo.findByIdAndUserUsername(orderId, userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
        Product product = serviceRepo.findByServiceName(ServiceName.valueOf(request.getServiceName()))
                .orElseThrow(
                        () -> new IllegalArgumentException("No service found with name: " + request.getServiceName()));

        order.setReference(request.getReference());
        order.setCategory(OrderCategory.valueOf(request.getCategory()));
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        return OrderDto.from(order);
    }

    @Transactional
    public void delete(Long orderId, UserDetails userDetails) {
        Order order = orderRepo.findByIdAndUserUsername(orderId, userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
        orderRepo.delete(order);
    }
}
