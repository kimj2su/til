package com.jisu.testcodewitharchitecture.mock;

import com.jisu.testcodewitharchitecture.common.service.UuidHolder;

public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    public TestUuidHolder(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String random() {
        return uuid;
    }
}
