package com.example.adapter.axon.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
@Builder
public class UpdateFirmbankingRequestCommand {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private final int firmbankingStatus;
}
