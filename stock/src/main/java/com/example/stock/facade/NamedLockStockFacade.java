package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService service;

    public NamedLockStockFacade(LockRepository lockRepository, StockService service) {
        this.lockRepository = lockRepository;
        this.service = service;
    }

    @Transactional
    public void decrease(Long id, Long quantity) throws InterruptedException {
        try {
            lockRepository.getLock(id.toString());
            service.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
