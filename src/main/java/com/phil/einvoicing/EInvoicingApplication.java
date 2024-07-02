package com.phil.einvoicing;

import com.phil.einvoicing.auth.AuthenticationService;
import com.phil.einvoicing.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.phil.einvoicing.user.Role.ADMIN;

@SpringBootApplication
public class EInvoicingApplication {


    public static void main(String[] args) {
        SpringApplication.run(EInvoicingApplication.class, args);
    }

//    static String token = "n";

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .names("muhire jack")
                    .username("admin")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//            token = service.register(admin).getAccessToken();

        };
    }
}
