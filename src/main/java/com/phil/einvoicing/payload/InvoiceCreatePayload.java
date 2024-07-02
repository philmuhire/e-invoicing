package com.phil.einvoicing.payload;

import com.phil.einvoicing.entity.Customer;
import com.phil.einvoicing.entity.InvoiceStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceCreatePayload {
    private String customerId;
    private String amount;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
