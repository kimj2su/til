package com.example.application.port.in;

import com.example.common.SelfValidating;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

    @NotNull
    private String membershipId;
    @NotNull
    private String bankName;
    @NotNull
    private String bankAccountNumber;
    @NotNull
    private boolean linkedStatusIsValid;

    public RegisterBankAccountCommand(@NotNull String membershipId, @NotNull String bankName, @NotNull String bankAccountNumber, @NotNull boolean linkedStatusIsValid) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkedStatusIsValid = linkedStatusIsValid;

        this.validateSelf();
    }
}
