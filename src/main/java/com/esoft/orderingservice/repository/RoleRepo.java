package com.esoft.orderingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esoft.orderingservice.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
    
}
