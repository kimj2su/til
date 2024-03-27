package com.example.application.port.in;

import com.example.adapter.out.persistance.MemberMoneyJpaEntity;
import com.example.domain.MemberMoney;

public interface GetMemberMoneyPort {

    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MembershipId membershipId
    );
}
