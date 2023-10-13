package com.example.membership.application.port.out;

import com.example.membership.apater.out.persistance.MemberShipJpaEntity;
import com.example.membership.domain.Membership;

public interface RegisterMembershipPort {

    MemberShipJpaEntity createMembership(
            Membership.MembershipName membershipName,
            Membership.MemberShipEmail memberShipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipisValid membershipIsValid,
            Membership.MembershipCorp membershipCorp
    );
}
