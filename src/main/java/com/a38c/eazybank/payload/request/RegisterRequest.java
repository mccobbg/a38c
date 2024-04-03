package com.a38c.eazybank.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
}
