package com.example.restdocs.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberSignupRequest {

    @NotEmpty
    private String name;
    @Email
    private String email;

    public Member toEntity() {
        return new Member(email, name);
    }
}
