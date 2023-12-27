package com.example.adapter.out.external.bank;

import lombok.Data;

@Data
public class GetBanAccountRequest {

    private String bankName;
    private String bankAccountNumber;

    public GetBanAccountRequest(String bankName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }
}
