package com.example.membership.apater.out.persistance;

import com.example.membership.application.port.out.FindMembershipPort;
import com.example.membership.application.port.out.RegisterMembershipPort;
import com.example.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository springDataMembershipRepository;
    @Override
    public MemberShipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MemberShipEmail memberShipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipisValid membershipIsValid, Membership.MembershipCorp membershipCorp) {
        return springDataMembershipRepository.save(
                new MemberShipJpaEntity(
                        membershipName.getMembershipName(),
                        memberShipEmail.getMemberShipEmail(),
                        membershipAddress.getMembershipAddress(),
                        membershipIsValid.isMembershipisValid(),
                        membershipCorp.isMembershipCorp()
                )
        );
    }

    @Override
    public MemberShipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return springDataMembershipRepository.getReferenceById(Long.parseLong(membershipId.getMembershipId()));
    }
}
