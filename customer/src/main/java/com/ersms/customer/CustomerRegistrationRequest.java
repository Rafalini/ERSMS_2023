package com.ersms.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
