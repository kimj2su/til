package com.example.membership.application.port.in;

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
public class ModifyMembershipCommand extends SelfValidating<RegisterMembershipCommand> {

    @NotNull
    private final String membershipId;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @NotNull
    @NotBlank
    private final String address;

    @AssertTrue
    private final boolean isValid;

    private final boolean isCorp;

    public ModifyMembershipCommand(@NotNull String membershipId, @NotNull String name, @NotNull String email, @NotNull String address, boolean isValid, boolean isCorp) {
        this.membershipId = membershipId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf();
    }
}