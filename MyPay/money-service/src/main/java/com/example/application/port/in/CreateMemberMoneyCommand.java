package com.example.application.port.in;

import com.example.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

    @NotNull
    private final String membershipId;

    public CreateMemberMoneyCommand(@NotNull String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}
