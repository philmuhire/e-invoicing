package com.phil.einvoicing.repository;

import com.phil.einvoicing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
