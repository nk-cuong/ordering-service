package com.esoft.orderingservice.service.order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        public List<OrderDto> getAll(String username) {
                return orderRepo.findByUserUsername(username)
                                .stream()
                                .map(OrderDto::from)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public OrderDto getById(Long orderId, String username) {
                return orderRepo.findByIdAndUserUsername(orderId, username)
                                .map(OrderDto::from)
                                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
        }

        @Transactional
        public OrderDto create(OrderRequest request, String username) {
                User user = userRepo.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "No user found with username: " + username));
                Product product = serviceRepo.findByServiceName(ServiceName.valueOf(request.getServiceName()))
                                .orElseThrow(
                                                () -> new IllegalArgumentException("No service found with name: "
                                                                + request.getServiceName()));

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
        public OrderDto update(Long orderId, OrderRequest request, String username) {
                Order order = orderRepo.findByIdAndUserUsername(orderId, username)
                                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
                Product product = serviceRepo.findByServiceName(ServiceName.valueOf(request.getServiceName()))
                                .orElseThrow(
                                                () -> new IllegalArgumentException("No service found with name: "
                                                                + request.getServiceName()));

                order.setReference(request.getReference());
                order.setCategory(OrderCategory.valueOf(request.getCategory()));
                order.setProduct(product);
                order.setQuantity(request.getQuantity());
                return OrderDto.from(order);
        }

        @Transactional
        public void delete(Long orderId, String username) {
                Order order = orderRepo.findByIdAndUserUsername(orderId, username)
                                .orElseThrow(() -> new IllegalArgumentException("No order found with id: " + orderId));
                orderRepo.delete(order);
        }
}
