package com.example.apater.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMoneyChangingRequestRepository extends JpaRepository<MoneyChangingRequestJpaEntity, Long> {
}