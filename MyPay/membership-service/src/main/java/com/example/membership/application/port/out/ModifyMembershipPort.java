package com.example.membership.application.port.out;

import com.example.membership.apater.out.persistance.MemberShipJpaEntity;
import com.example.membership.domain.Membership;

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
