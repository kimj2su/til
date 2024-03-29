package com.example.adapter.in.web;

import com.example.application.port.in.RequestFirmbankingCommand;
import com.example.application.port.in.RequestFirmbankingUseCase;
import com.example.application.port.in.UpdateFirmbankingCommand;
import com.example.application.port.in.UpdateFirmbankingUseCase;
import com.example.common.WebAdapter;
import com.example.domain.FirmbankingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUsecase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUsecase.requestFirmbanking(command);
    }

    @PostMapping("/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        requestFirmbankingUsecase.requestFirmbankingByEvent(command);
    }

    @PutMapping("/banking/firmbanking/update-eda")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingRequestAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus())
                .build();

        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }
}
