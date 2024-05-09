package com.jisu.testcodewitharchitecture.common.infrastructure;

import com.jisu.testcodewitharchitecture.common.service.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
