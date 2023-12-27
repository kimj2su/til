package com.example.application.service;

import com.example.adapter.out.external.bank.BankAccount;
import com.example.adapter.out.external.bank.GetBanAccountRequest;
import com.example.adapter.out.persistance.RegisteredBankAccountJpaEntity;
import com.example.adapter.out.persistance.RegisteredBankAccountPersistenceMapper;
import com.example.application.port.in.RegisterBankAccountCommand;
import com.example.application.port.in.RegisterBankAccountUseCase;
import com.example.application.port.out.RegisterBankAccountPort;
import com.example.application.port.out.RequestBankAccountInfoPort;
import com.example.common.UseCase;
import com.example.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountPersistenceMapper mapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {
        // 은행 계좌를 등록해야하는 서비스(비즈니스 로직)
        // 1. 등록된 계좌인지 확인한다.
        // 외부의 은행에 이 계좌 정상인지? 확인
        // Biz Logic -> External System
        // Port -> Adapter -> External System
        // Port

        // 실제 외부의 은행계좌 정보를 Get
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBanAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean accountIsValid = accountInfo.isValid();

        // 2. 등록가능한 계좌라면, 등록한다. 등록에 성공한 등록 정보를 리턴
        // 2-1. 등록간으하지 않은 계좌라면, 에러를 리턴
        if (accountIsValid) {

            RegisteredBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId() + ""),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
            );

            return mapper.mapToDomainEntity(savedAccountInfo);
        } else {
            return null;
        }
    }
}
