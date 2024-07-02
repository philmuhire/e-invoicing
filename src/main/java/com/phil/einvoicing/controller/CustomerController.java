package com.phil.einvoicing.controller;

import com.phil.einvoicing.entity.Customer;
import com.phil.einvoicing.entity.Invoice;
import com.phil.einvoicing.payload.CustomerCreatePayload;
import com.phil.einvoicing.rabbit.RabbitMQSender;
import com.phil.einvoicing.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerController {
    private final CustomerRepository customerRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PreAuthorize("hasRole('ADMIN')")
   @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerCreatePayload request){
       URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/customer").toUriString());
       Customer customer;
       try {
           customer = customerRepository.save(Customer.builder()
                           .name(request.getName())
                           .email(request.getEmail())
                           .phoneNumber(request.getPhoneNumber())
                   .build());

       } catch (Exception exception) {
           return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
       }
       return ResponseEntity.created(uri).body(customer);
   }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Customer request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/customer").toUriString());
        Customer customer;
        try {
            customer = customerRepository.save(Customer.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .build());
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Optional<Customer> customer;
        try {
            customer = customerRepository.findById(id);
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Customer> customers;
        try {
            customers = customerRepository.findAll();
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.ok(customers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            customerRepository.deleteById(id);
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }

        return ResponseEntity.ok("Customer deleted successfully");
    }


}
