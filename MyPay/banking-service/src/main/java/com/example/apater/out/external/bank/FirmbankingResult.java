package com.example.apater.out.external.bank;

import lombok.Data;

@Data
public class FirmbankingResult {

    private int resultCode;

    public FirmbankingResult(int resultCode) {
        this.resultCode = resultCode;
    }
}
