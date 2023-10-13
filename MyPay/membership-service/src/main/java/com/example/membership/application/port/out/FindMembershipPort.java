package com.example.membership.application.port.out;

import com.example.membership.apater.out.persistance.MemberShipJpaEntity;
import com.example.membership.domain.Membership;

public interface FindMembershipPort {
    MemberShipJpaEntity findMembership(Membership.MembershipId membershipId);
}
