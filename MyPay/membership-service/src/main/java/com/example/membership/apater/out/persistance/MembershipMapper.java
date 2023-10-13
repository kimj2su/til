package com.example.membership.apater.out.persistance;

import com.example.membership.domain.Membership;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {
    public Membership mapToDomainEntity(MemberShipJpaEntity memberShipJpaEntity) {
      return Membership.generateMember(
              new Membership.MembershipId(memberShipJpaEntity.getMembershipId()+""),
              new Membership.MembershipName(memberShipJpaEntity.getName()),
              new Membership.MemberShipEmail(memberShipJpaEntity.getEmail()),
              new Membership.MembershipAddress(memberShipJpaEntity.getAddress()),
              new Membership.MembershipisValid(memberShipJpaEntity.isValid()),
              new Membership.MembershipCorp(memberShipJpaEntity.isCorp())
      );
    }
}
