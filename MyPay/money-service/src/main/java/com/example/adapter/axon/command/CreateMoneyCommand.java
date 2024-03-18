package com.example.adapter.axon.command;

import com.example.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateMoneyCommand extends SelfValidating<CreateMoneyCommand> {

    @NotNull
    private String membershipId;

    public CreateMoneyCommand() {
    }

    public CreateMoneyCommand(@NotNull String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}
