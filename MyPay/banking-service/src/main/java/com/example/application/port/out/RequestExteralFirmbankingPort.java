package com.example.application.port.out;

import com.example.apater.out.external.bank.ExternalFirmbankingRequest;
import com.example.apater.out.external.bank.FirmbankingResult;

public interface RequestExteralFirmbankingPort {

    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
