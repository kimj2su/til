package com.example.apater.out.external.bank;

import com.example.apater.out.persistance.SpringDataRegisteredBankAccountRepository;
import com.example.application.port.out.RequestBankAccountInfoPort;
import com.example.common.ExternalSystem;
import lombok.RequiredArgsConstructor;

@ExternalSystem
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {

    @Override
    public BankAccount getBankAccountInfo(GetBanAccountRequest request) {
        // 실제로 외부 은행에 http 을 통해서
        // 실제 은행 계좌 정보를 가져오고

        // 실제 은행 계좌 -> BankAccount
        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
