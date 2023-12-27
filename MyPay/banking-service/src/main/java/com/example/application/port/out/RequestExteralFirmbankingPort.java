package com.example.application.port.out;

import com.example.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.example.adapter.out.external.bank.FirmbankingResult;

public interface RequestExteralFirmbankingPort {

    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
