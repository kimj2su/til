package com.example.application.port.in;

import com.example.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<UpdateFirmbankingCommand> {

    @NotNull
    private final String firmbankingRequestAggregateIdentifier;

    @NotNull
    private final int firmbankingStatus;
}
