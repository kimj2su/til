package com.example.membership.application.port.in;

import com.example.membership.domain.Membership;

public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);
}
