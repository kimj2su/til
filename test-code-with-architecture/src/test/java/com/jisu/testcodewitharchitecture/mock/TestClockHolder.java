package com.jisu.testcodewitharchitecture.mock;

import com.jisu.testcodewitharchitecture.common.service.ClockHolder;

public class TestClockHolder implements ClockHolder {

    private final long millis;

    public TestClockHolder(long millis) {
        this.millis = millis;
    }

    @Override
    public long millis() {
        return millis;
    }
}
