package com.phil.einvoicing.integration;

import com.phil.einvoicing.entity.Customer;
import com.phil.einvoicing.entity.InvoiceStatus;
import com.phil.einvoicing.payload.InvoiceCreatePayload;
import com.phil.einvoicing.repository.CustomerRepository;
import com.phil.einvoicing.repository.InvoiceRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setup() {
        invoiceRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateInvoice() throws Exception {
        // Given

        Customer customer = customerRepository.save(Customer.builder()
                        .name("karenzi")
                        .email("jack")
                        .phoneNumber("0797654312").build());
        InvoiceCreatePayload payload = InvoiceCreatePayload.builder().customerId(Long.toString(customer.getId())).amount("3000").status(InvoiceStatus.PAID).build();

        // When & Then
        mockMvc.perform(post("/api/v1/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer.id").value(customer.getId()))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    public void testCreateInvoiceWithInvalidCustomer() throws Exception {
        // Given
        InvoiceCreatePayload payload = InvoiceCreatePayload.builder().customerId("8888").amount("3000").status(InvoiceStatus.PAID).build();


        // When & Then
        mockMvc.perform(post("/api/v1/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}

