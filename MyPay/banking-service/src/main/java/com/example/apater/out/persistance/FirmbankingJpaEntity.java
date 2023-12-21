package com.example.apater.out.persistance;

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
    private int moneyAmount;
    private String firmbankingStatus;
    private String uuid;

    public FirmbankingJpaEntity(String formBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, String firmbankingStatus, UUID uuid) {
        this.formBankName = formBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmbankingStatus = firmbankingStatus;
        this.uuid = uuid.toString();
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
                ", uuid=" + uuid +
                '}';
    }
}
