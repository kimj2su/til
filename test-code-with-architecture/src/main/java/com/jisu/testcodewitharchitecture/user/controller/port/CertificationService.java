package com.jisu.testcodewitharchitecture.user.controller.port;

public interface CertificationService {
    void send(String email, long id, String certificationCode);
}
