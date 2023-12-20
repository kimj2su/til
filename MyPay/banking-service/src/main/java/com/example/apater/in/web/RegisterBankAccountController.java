package com.example.apater.in.web;

import com.example.application.port.in.RegisterBankAccountCommand;
import com.example.application.port.in.RegisterBankAccountUseCase;
import com.example.common.WebAdapter;
import com.example.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registeredBankAccountUseCase;

    @PostMapping("/banking/account/register")
    RegisteredBankAccount register(@RequestBody RegisterBankAccountRequest request) {
        // request

        // Usecase
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isLinkedStatusIsValid())
                .build();
        RegisteredBankAccount registeredBankAccount = registeredBankAccountUseCase.registerBankAccount(command);
        if (registeredBankAccount == null) {
            // ToDo: Error handling
            return null;
        }
        return registeredBankAccount;
    }
}
