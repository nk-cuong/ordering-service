package com.esoft.orderingservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esoft.orderingservice.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByUserUsername(String username);

    Optional<Order> findByIdAndUserUsername(Long orderId, String username);

    @Query("SELECT o FROM Order o " +
            " WHERE (:userId IS NULL OR o.user.id = :userId) " +
            " AND (:fromDate IS NULL OR o.createdDate >= :fromDate) " +
            " AND (:toDate IS NULL OR o.createdDate <= :toDate) ")
    List<Order> getOrderStatistic(Long userId, LocalDate fromDate, LocalDate toDate);
}
