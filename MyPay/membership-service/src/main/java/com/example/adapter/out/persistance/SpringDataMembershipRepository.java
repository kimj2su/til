package com.example.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<MemberShipJpaEntity, Long> {
}
