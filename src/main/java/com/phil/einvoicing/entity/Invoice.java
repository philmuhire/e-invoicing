package com.phil.einvoicing.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity(name = "Invoice")
@Table(name = "invoices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @CreatedDate
    @Column(insertable = false)
    private LocalDateTime invoiceDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;
    private InvoiceStatus status;

}
