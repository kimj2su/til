package com.jisu.testcodewitharchitecture.common.infrastructure;

import com.jisu.testcodewitharchitecture.common.service.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }
}
