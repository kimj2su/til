package com.example.application.service;

import com.example.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.example.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.example.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.example.adapter.out.external.bank.FirmbankingResult;
import com.example.adapter.out.persistance.FirmbankingJpaEntity;
import com.example.adapter.out.persistance.FirmbankingMapper;
import com.example.application.port.in.RequestFirmbankingCommand;
import com.example.application.port.in.RequestFirmbankingUseCase;
import com.example.application.port.in.UpdateFirmbankingCommand;
import com.example.application.port.in.UpdateFirmbankingUseCase;
import com.example.application.port.out.RequestExteralFirmbankingPort;
import com.example.application.port.out.RequestFirmbankingPort;
import com.example.common.UseCase;
import com.example.domain.FirmbankingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {

    private final FirmbankingMapper firmbankingMapper;
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final RequestExteralFirmbankingPort requestExteralFirmbankingPort;
    private final CommandGateway commandGateway;

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
                new FirmbankingRequest.FirmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier("")
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
            requestedEntity.setFirmbankingStatus(1);
        } else {
            requestedEntity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴하기 전에 상태를 확인
        return firmbankingMapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }

    @Override
    public void requestFirmbankingByEvent(RequestFirmbankingCommand command) {
        // Command -> Event Sourcing
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .build();

        commandGateway.send(createFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error: " + throwable.getMessage());
                        throwable.printStackTrace();
                    } else {
                        System.out.println("Success: " + result);

                        // 1. 요청에 대해 정보를 먼저 write. "0" 상태로
                        FirmbankingJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                                new FirmbankingRequest.ToBankName(command.getToBankName()),
                                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                                new FirmbankingRequest.FirmbankingStatus(0),
                                new FirmbankingRequest.FirmbankingAggregateIdentifier(result.toString())
                        );

                        // 2. 외부 은행에 펌뱅킹 은행
                        FirmbankingResult firmbankingResult = requestExteralFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
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
                        if (firmbankingResult.getResultCode() == 0) {
                            requestedEntity.setFirmbankingStatus(1);
                        } else {
                            requestedEntity.setFirmbankingStatus(2);
                        }

                        requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity);
                    }
                });
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {
        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand =
                new UpdateFirmbankingRequestCommand(command.getFirmbankingRequestAggregateIdentifier(), command.getFirmbankingStatus());

        commandGateway.send(updateFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error: " + throwable.getMessage());
                        throwable.printStackTrace();
                    } else {
                        System.out.println("Success: " + result);
                        FirmbankingJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                                new FirmbankingRequest.FirmbankingAggregateIdentifier(command.getFirmbankingRequestAggregateIdentifier()));

                        // status 변경으로 인한 외부 은행과의 커뮤니케이션
                        // if rollback -> 0, status 변경도 함
                        // 기존 펌뱅킹 정보에서 from <-> to 가 변경된 펌뱅킹을 요청하는 새로운 요청
                        System.out.println("Update Firmbanking Status: " + command.getFirmbankingStatus());
                        entity.setFirmbankingStatus(command.getFirmbankingStatus());
                        requestFirmbankingPort.modifyFirmbankingRequest(entity);
                    }
                });
    }
}
