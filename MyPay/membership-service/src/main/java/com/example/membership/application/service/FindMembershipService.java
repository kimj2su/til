package com.example.membership.application.service;

import com.example.membership.apater.out.persistance.MemberShipJpaEntity;
import com.example.membership.apater.out.persistance.MembershipMapper;
import com.example.membership.application.port.in.FindMembershipCommand;
import com.example.membership.application.port.in.FindMembershipUseCase;
import com.example.membership.application.port.out.FindMembershipPort;
import com.example.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MemberShipJpaEntity entity = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }
}
