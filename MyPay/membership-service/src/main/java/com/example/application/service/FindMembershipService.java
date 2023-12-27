package com.example.application.service;

import com.example.common.UseCase;
import com.example.adapter.out.persistance.MemberShipJpaEntity;
import com.example.adapter.out.persistance.MembershipMapper;
import com.example.application.port.in.FindMembershipCommand;
import com.example.application.port.in.FindMembershipUseCase;
import com.example.application.port.out.FindMembershipPort;
import com.example.domain.Membership;
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
