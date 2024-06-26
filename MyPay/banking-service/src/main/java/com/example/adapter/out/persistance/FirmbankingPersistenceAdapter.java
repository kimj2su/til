package com.example.adapter.out.persistance;

import com.example.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.example.adapter.out.external.bank.FirmbankingResult;
import com.example.application.port.out.RequestExteralFirmbankingPort;
import com.example.application.port.out.RequestFirmbankingPort;
import com.example.common.PersistenceAdapter;
import com.example.domain.FirmbankingRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingPersistenceAdapter implements RequestFirmbankingPort, RequestExteralFirmbankingPort {

    private final SpringDataFirmbankingRepository firmbankingRepository;

    @Override
    public FirmbankingJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber, FirmbankingRequest.MoneyAmount moneyAmount, FirmbankingRequest.FirmbankingStatus firmbankingStatus, FirmbankingRequest.FirmbankingAggregateIdentifier aggregateIdentifier) {
        FirmbankingJpaEntity entity = firmbankingRepository.save(new FirmbankingJpaEntity(
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                UUID.randomUUID(),
                aggregateIdentifier.getAggregateIdentifier())
        );
        return entity;
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {

        // 실제로 외부 은행에 http 통신을 통해서
        // 펌뱅킹 요청을하고

        // 그 결과를
        // 외부 은행의 실제 결과를 -> FirmbankingResult로 받는다.
        return new FirmbankingResult(0);
    }


    @Override
    public FirmbankingJpaEntity modifyFirmbankingRequest(FirmbankingJpaEntity entity) {
        return firmbankingRepository.save(entity);
    }

    @Override
    public FirmbankingJpaEntity getFirmbankingRequest(FirmbankingRequest.FirmbankingAggregateIdentifier aggregateIdentifier) {
        List<FirmbankingJpaEntity> entityList = firmbankingRepository.findByAggregateIdentifier(aggregateIdentifier.getAggregateIdentifier());
        if (entityList.size() >= 1) {
            return entityList.get(0);
        }
        return null;
    }
}
