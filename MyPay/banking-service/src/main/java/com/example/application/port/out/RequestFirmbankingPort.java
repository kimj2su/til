package com.example.application.port.out;

import com.example.adapter.out.persistance.FirmbankingJpaEntity;
import com.example.domain.FirmbankingRequest;

public interface RequestFirmbankingPort {

    FirmbankingJpaEntity createFirmbankingRequest(
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAmount moneyAmount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus
    );

    FirmbankingJpaEntity modifyFirmbankingRequest(
            FirmbankingJpaEntity entity
    );
}
