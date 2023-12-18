package com.example.membership.application.service;

import com.example.common.UseCase;
import com.example.membership.apater.out.persistance.MemberShipJpaEntity;
import com.example.membership.apater.out.persistance.MembershipMapper;
import com.example.membership.application.port.in.ModifyMembershipCommand;
import com.example.membership.application.port.in.ModifyMembershipUseCase;
import com.example.membership.application.port.out.ModifyMembershipPort;
import com.example.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        // DB와 통신을 하려고 보니 DB는 외부 통신이므로 port, adapter를 만들어야 한다.
        MemberShipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MemberShipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipisValid(command.isValid()),
                new Membership.MembershipCorp(command.isCorp())
        );

        // entity -> Memebership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
