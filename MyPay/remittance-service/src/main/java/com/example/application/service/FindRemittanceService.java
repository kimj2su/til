package com.example.application.service;

import com.example.adapter.out.persistence.RemittanceRequestMapper;
import com.example.application.port.in.FindRemittanceCommand;
import com.example.application.port.in.FindRemittanceUseCase;
import com.example.application.port.out.FindRemittancePort;
import com.example.common.UseCase;
import com.example.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        //
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}