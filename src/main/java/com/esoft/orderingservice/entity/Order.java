package com.esoft.orderingservice.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import com.esoft.orderingservice.constant.OrderCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reference", unique = true)
    private String reference;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private OrderCategory category;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    @ColumnDefault("0")
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_date")
    private LocalDate createdDate;

    public long getTotalAmount() {
        return product.getPrice() * quantity;
    }
}
