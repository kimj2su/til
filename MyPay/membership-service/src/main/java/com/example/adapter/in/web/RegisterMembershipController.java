package com.example.adapter.in.web;

import com.example.common.WebAdapter;
import com.example.application.port.in.RegisterMembershipCommand;
import com.example.application.port.in.RegisterMembershipUseCase;
import com.example.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @GetMapping("/test")
    void test() {
        System.out.println("Hello world!");
    }

    @PostMapping("/membership/register")
    Membership register(@RequestBody RegisterMemberShipRequest request) {
        // request

        // Usecase

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        return registerMembershipUseCase.registerMembership(command);
    }
}
