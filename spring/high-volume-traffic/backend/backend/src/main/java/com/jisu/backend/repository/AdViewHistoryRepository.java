package com.jisu.backend.repository;

import com.jisu.backend.entity.AdViewHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdViewHistoryRepository extends MongoRepository<AdViewHistory, String> {

}