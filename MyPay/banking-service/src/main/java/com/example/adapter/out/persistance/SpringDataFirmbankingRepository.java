package com.example.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataFirmbankingRepository extends JpaRepository<FirmbankingJpaEntity, Long> {

    @Query("SELECT e FROM FirmbankingJpaEntity e WHERE e.aggregateIdentifier = :aggregateIdentifier")
    List<FirmbankingJpaEntity> findByAggregateIdentifier(@Param("aggregateIdentifier") String aggregateIdentifier);
}
