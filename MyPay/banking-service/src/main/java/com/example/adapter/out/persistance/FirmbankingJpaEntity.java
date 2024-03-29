package com.example.adapter.out.persistance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "request_firmbanking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirmbankingJpaEntity {

    @Id
    @GeneratedValue
    private Long requestFirmbankingId;
    private String formBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount; // only won

    private int firmbankingStatus; // 0: 요청, 1: 완료, 2: 실패
    private String uuid;
    private String aggregateIdentifier;

    public FirmbankingJpaEntity(String formBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, int firmbankingStatus, UUID uuid, String aggregateIdentifier) {
        this.formBankName = formBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmbankingStatus = firmbankingStatus;
        this.uuid = uuid.toString();
        this.aggregateIdentifier = aggregateIdentifier;
    }

    @Override
    public String toString() {
        return "FirmbankingJpaEntity{" +
                "requestFirmbankingId=" + requestFirmbankingId +
                ", formBankName='" + formBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", firmbankingStatus='" + firmbankingStatus + '\'' +
                ", uuid='" + uuid + '\'' +
                ", aggregateIdentifier='" + aggregateIdentifier + '\'' +
                '}';
    }
}
