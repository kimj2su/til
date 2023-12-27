package com.example.application.service;

import com.example.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.example.adapter.out.external.bank.FirmbankingResult;
import com.example.adapter.out.persistance.FirmbankingJpaEntity;
import com.example.adapter.out.persistance.FirmbankingMapper;
import com.example.application.port.in.RequestFirmbankingCommand;
import com.example.application.port.in.RequestFirmbankingUsecase;
import com.example.application.port.out.RequestExteralFirmbankingPort;
import com.example.application.port.out.RequestFirmbankingPort;
import com.example.common.UseCase;
import com.example.domain.FirmbankingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUsecase {

    private final FirmbankingMapper firmbankingMapper;
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final RequestExteralFirmbankingPort requestExteralFirmbankingPort;

    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {

        // Business Logic
        // a-> b

        // 1. 요청에 대해 정보를 먼저 write. "0" 상태로
        FirmbankingJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus("0")
        );

        // 2. 외부 은행에 펌뱅킹 은행
        FirmbankingResult result = requestExteralFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount(),
                "0"
        ));

        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());
        // 3. 결가에 따라서 1번에 작성했던 FirmbankingRequest 정보를 Update
        if (result.getResultCode() == 0) {
            requestedEntity.setFirmbankingStatus("1");
        } else {
            requestedEntity.setFirmbankingStatus("2");
        }

        // 4. 결과를 리턴하기 전에 상태를 확인
        return firmbankingMapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }
}
