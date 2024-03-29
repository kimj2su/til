package com.example.adapter.axon.aggregate;

import com.example.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.example.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.example.adapter.axon.event.FirmbankingRequestCreateEvent;
import com.example.adapter.axon.event.FirmbankingRequestUpdateEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
public class FirmbankingRequestAggregate {

    @AggregateIdentifier
    private String id;

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount;

    private int firmbankingStatus;

    public FirmbankingRequestAggregate() {
    }

    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command) {
        System.out.println("CreateFirmbankingRequestCommand Handler");
        apply(new FirmbankingRequestCreateEvent(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount())
        );
    }

    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command) {
        System.out.println("UpdateFirmbankingRequestCommand Handler");

        id = command.getAggregateIdentifier();
        apply(new FirmbankingRequestUpdateEvent(command.getFirmbankingStatus()));

        return id;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestCreateEvent event) {
        this.id = UUID.randomUUID().toString();
        this.fromBankName = event.getFromBankName();
        this.fromBankAccountNumber = event.getFromBankAccountNumber();
        this.toBankName = event.getToBankName();
        this.toBankAccountNumber = event.getToBankAccountNumber();
        this.moneyAmount = event.getMoneyAmount();
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdateEvent event) {
        System.out.println("FirmbankingRequestUpdateEvent Sourcing Handler");
        this.firmbankingStatus = event.getFirmbankingStatus();
    }
}
