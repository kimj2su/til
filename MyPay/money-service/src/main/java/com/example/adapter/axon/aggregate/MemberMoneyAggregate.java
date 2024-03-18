package com.example.adapter.axon.aggregate;

import com.example.adapter.axon.command.CreateMoneyCommand;
import com.example.adapter.axon.event.MemberMoneyCreateEvent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(@NotNull CreateMoneyCommand command) {
        System.out.println("CreateMoneyCommand Handler");
        // store event
        apply(new MemberMoneyCreateEvent(command.getMembershipId()));
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreateEvent event) {
        System.out.println("MemberMoneyCreateEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    // @EventSourcingHandler
    // public void on(IncreaseMoneyEvent event) {
    //     System.out.println("IncreaseMoneyEvent Sourcing Handler");
    //     id = event.getAggregateIdentifier();
    //     membershipId = Long.parseLong(event.getTargetMembershipId());
    //     balance = event.getAmount();
    // }
    //
    // public MemberMoneyAggregate() {
    //     // Required by Axon to construct an empty instance to initiate Event Sourcing.
    // }
    //
    // @Override
    // public String toString() {
    //     return "MemberMoneyAggregate{" +
    //             "id='" + id + '\'' +
    //             ", membershipId=" + membershipId +
    //             ", balance=" + balance +
    //             '}';
    // }
}
