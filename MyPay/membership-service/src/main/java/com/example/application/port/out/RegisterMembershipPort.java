package com.example.application.port.out;

import com.example.adapter.out.persistance.MemberShipJpaEntity;
import com.example.domain.Membership;

public interface RegisterMembershipPort {

    MemberShipJpaEntity createMembership(
            Membership.MembershipName membershipName,
            Membership.MemberShipEmail memberShipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipisValid membershipIsValid,
            Membership.MembershipCorp membershipCorp
    );
}
