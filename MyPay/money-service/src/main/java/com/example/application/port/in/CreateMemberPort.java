package com.example.application.port.in;

import com.example.domain.MemberMoney;

public interface CreateMemberPort {

    void createMember(
            MemberMoney.MembershipId memberId,
            MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
    );
}
