package com.assignment1.librarymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI libraryManagementOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Library Management API")
                        .description("""
                                REST APIs for Library Management System.

                                Features:
                                • CRUD Operations
                                • Pagination
                                • Sorting
                                • Library Reports
                                • Health Check
                                • Validation
                                • Role-based Authorization
                                • Caching
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kolli Sai Charan Reddy")
                                .email("saicharan.reddy@p99soft.com"))
                        .license(new License()
                                .name("Apache 2.0")));
    }
}