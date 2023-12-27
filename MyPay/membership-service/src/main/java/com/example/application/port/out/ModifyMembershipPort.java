package com.example.application.port.out;

import com.example.adapter.out.persistance.MemberShipJpaEntity;
import com.example.domain.Membership;

public interface ModifyMembershipPort {

    MemberShipJpaEntity modifyMembership(
            Membership.MembershipId membershipId,
            Membership.MembershipName membershipName,
            Membership.MemberShipEmail memberShipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipisValid membershipIsValid,
            Membership.MembershipCorp membershipCorp
    );
}
