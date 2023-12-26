package com.example.application.port.in;

import com.example.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class IncreaseMoneyRequestCommand extends SelfValidating<IncreaseMoneyRequestCommand> {

    @NotNull
    private final String targetMembershipId;

    @NotNull
    private final int amount;


    public IncreaseMoneyRequestCommand(@NotNull String targetMembershipId, @NotNull int amount) {
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
