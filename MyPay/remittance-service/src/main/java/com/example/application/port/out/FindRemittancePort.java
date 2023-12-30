package com.example.application.port.out;

import com.example.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.example.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
