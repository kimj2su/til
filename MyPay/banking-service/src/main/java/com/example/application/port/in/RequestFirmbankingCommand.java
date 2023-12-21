package com.example.application.port.in;

import com.example.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class RequestFirmbankingCommand extends SelfValidating<RequestFirmbankingCommand> {

    @NotNull
    private final String fromBankName;
    @NotNull
    private final String fromBankAccountNumber;
    @NotNull
    private final String toBankName;
    @NotNull
    private final String toBankAccountNumber;
    @NotNull
    private final int moneyAmount;

    @Builder
    public RequestFirmbankingCommand(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.validateSelf();
    }

    public String getFromBankName() {
        return fromBankName;
    }

    public String getFromBankAccountNumber() {
        return fromBankAccountNumber;
    }

    public String getToBankName() {
        return toBankName;
    }

    public String getToBankAccountNumber() {
        return toBankAccountNumber;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }
}
