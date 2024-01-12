package com.example.mongodbpractice.team.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, Long> {
}
