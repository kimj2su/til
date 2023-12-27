package com.example.application.port.out;

import com.example.adapter.out.external.bank.BankAccount;
import com.example.adapter.out.external.bank.GetBanAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBanAccountRequest request9);
}
