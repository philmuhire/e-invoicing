package com.phil.einvoicing.payload;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class CustomerCreatePayload {
    private String name;
    private String email;
    private String phoneNumber;

}
