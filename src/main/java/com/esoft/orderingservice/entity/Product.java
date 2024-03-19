package com.esoft.orderingservice.entity;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.esoft.orderingservice.constant.ServiceName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "service_name", unique = true)
    @Enumerated(value = EnumType.STRING)
    private ServiceName serviceName;

    @Column(name = "price")
    @ColumnDefault("0")
    private long price;

    @OneToMany(mappedBy = "product")
    List<Order> orders;
}
