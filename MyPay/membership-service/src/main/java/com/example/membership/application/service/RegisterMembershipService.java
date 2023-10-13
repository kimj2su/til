package com.example.membership.application.service;

import com.example.membership.apater.out.persistance.MemberShipJpaEntity;
import com.example.membership.apater.out.persistance.MembershipMapper;
import com.example.membership.application.port.in.RegisterMembershipCommand;
import com.example.membership.application.port.in.RegisterMembershipUseCase;
import com.example.membership.application.port.out.RegisterMembershipPort;
import com.example.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {

        // DB와 통신을 하려고 보니 DB는 외부 통신이므로 port, adapter를 만들어야 한다.
        MemberShipJpaEntity jpaEntity = registerMembershipPort.createMembership(
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
