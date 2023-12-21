package com.example.apater.out.persistance;

import com.example.domain.FirmbankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingMapper {
    public FirmbankingRequest mapToDomainEntity(FirmbankingJpaEntity entity, UUID uuid) {
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmBankingRequestId(entity.getRequestFirmbankingId()+""),
                new FirmbankingRequest.FromBankName(entity.getFormBankName()),
                new FirmbankingRequest.FromBankAccountNumber(entity.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(entity.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(entity.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(entity.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(entity.getFirmbankingStatus()),
                uuid
        );
    }
}
