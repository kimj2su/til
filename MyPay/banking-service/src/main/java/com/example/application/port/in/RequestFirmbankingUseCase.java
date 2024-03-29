package com.example.application.port.in;

import com.example.domain.FirmbankingRequest;

public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command);
    void requestFirmbankingByEvent(RequestFirmbankingCommand command);
}
