package com.example.application.port.out;

import com.example.adapter.out.persistance.MemberShipJpaEntity;
import com.example.domain.Membership;

public interface FindMembershipPort {
    MemberShipJpaEntity findMembership(Membership.MembershipId membershipId);
}
