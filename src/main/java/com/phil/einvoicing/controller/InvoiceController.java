package com.phil.einvoicing.controller;

import com.phil.einvoicing.entity.Customer;
import com.phil.einvoicing.entity.Invoice;
import com.phil.einvoicing.payload.CustomerCreatePayload;
import com.phil.einvoicing.payload.InvoiceCreatePayload;
import com.phil.einvoicing.rabbit.RabbitMQSender;
import com.phil.einvoicing.repository.CustomerRepository;
import com.phil.einvoicing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    @Autowired
    private final InvoiceRepository invoiceRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody InvoiceCreatePayload request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/customer").toUriString());
        Optional<Customer> customer;
        Invoice invoice;
        try {
            customer = customerRepository.findById(Long.parseLong(request.getCustomerId()));
            invoice = invoiceRepository.save(Invoice.builder()
                    .customer(customer.orElse(null))
                    .amount(Double.parseDouble(request.getAmount()))
                    .status(request.getStatus())
                    .build());
            rabbitMQSender.sendInvoiceCreatedMessage(invoice);
        } catch (Exception exception) {
            System.out.println(exception);
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.created(uri).body(invoice);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody InvoiceCreatePayload request){
        Optional<Customer> customer;
        Invoice invoice;
        try {
            customer = customerRepository.findById(Long.parseLong(request.getCustomerId()));
            invoice = invoiceRepository.save(Invoice.builder()
                    .customer(customer.orElse(null))
                    .amount(Double.parseDouble(request.getAmount()))
                    .status(request.getStatus())
                    .build());
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Optional<Invoice> invoice;
        try {
            invoice = invoiceRepository.findById(id);
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.ok(invoice);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Invoice> invoices;
        try {
            invoices = invoiceRepository.findAll();
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }
        return ResponseEntity.ok(invoices);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            invoiceRepository.deleteById(id);
        } catch (Exception exception) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().header(exception.getMessage());
        }

        return ResponseEntity.ok("invoice deleted successfully");
    }



}
