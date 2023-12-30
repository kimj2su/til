package com.example.application.port.in;

import com.example.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindRemittanceCommand extends SelfValidating<FindRemittanceCommand> {

    @NotNull
    private String membershipId; // from membership

    public FindRemittanceCommand(String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}