package com.phil.einvoicing.integration;
import com.phil.einvoicing.entity.Customer;
import com.phil.einvoicing.entity.InvoiceStatus;
import com.phil.einvoicing.payload.CustomerCreatePayload;
import com.phil.einvoicing.payload.InvoiceCreatePayload;
import com.phil.einvoicing.repository.CustomerRepository;
import com.phil.einvoicing.repository.InvoiceRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phil.einvoicing.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private String token = tokenRepository.findAllValidTokenByUser(1).get(0).token;



    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        // Given
        Customer customer = customerRepository.save(Customer.builder().name("karenzi")
                .email("karenzi@mail.com")
                .phoneNumber("0789654321").build());
//        Token token = tokenRepository.findAllValidTokenByUser


        // When & Then
        mockMvc.perform(post("http://localhost:8080/api/v1/customer")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("karenzi"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // Given
        Customer customer = customerRepository.save(Customer.builder().name("karenzi")
                .email("karenzi@mail.com")
                .phoneNumber("0789654321").build());


        // When & Then
        mockMvc.perform(put("http://localhost:8080/api/v1/customer")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON )
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("karenzi"));
    }


}

