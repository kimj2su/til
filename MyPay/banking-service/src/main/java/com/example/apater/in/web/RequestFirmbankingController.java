package com.example.apater.in.web;

import com.example.application.port.in.RequestFirmbankingCommand;
import com.example.application.port.in.RequestFirmbankingUsecase;
import com.example.common.WebAdapter;
import com.example.domain.FirmbankingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUsecase requestFirmbankingUsecase;

    @PostMapping("/banking/firmbanking/request")
    FirmbankingRequest requestFirmbanking(RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUsecase.requestFirmbanking(command);
    }

}
