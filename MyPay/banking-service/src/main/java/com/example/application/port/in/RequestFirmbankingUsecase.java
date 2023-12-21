package com.example.application.port.in;

import com.example.domain.FirmbankingRequest;

public interface RequestFirmbankingUsecase {

    FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command);
}
