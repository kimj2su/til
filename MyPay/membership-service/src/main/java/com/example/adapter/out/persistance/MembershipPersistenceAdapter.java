package com.example.adapter.out.persistance;

import com.example.common.PersistenceAdapter;
import com.example.application.port.out.FindMembershipPort;
import com.example.application.port.out.ModifyMembershipPort;
import com.example.application.port.out.RegisterMembershipPort;
import com.example.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    @Override
    public MemberShipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MemberShipEmail memberShipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipisValid membershipIsValid, Membership.MembershipCorp membershipCorp) {
        return membershipRepository.save(
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
        return membershipRepository.getReferenceById(Long.parseLong(membershipId.getMembershipId()));
    }

    @Override
    public MemberShipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MemberShipEmail memberShipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipisValid membershipIsValid, Membership.MembershipCorp membershipCorp) {
        MemberShipJpaEntity entity = membershipRepository.getReferenceById(Long.parseLong(membershipId.getMembershipId()));

        entity.setName(membershipName.getMembershipName());
        entity.setEmail(memberShipEmail.getMemberShipEmail());
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setValid(membershipIsValid.isMembershipisValid());
        entity.setCorp(membershipCorp.isMembershipCorp());

        return membershipRepository.save(entity);
    }
}
