package com.example.application.port.in;

import com.example.domain.RegisteredBankAccount;


public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
