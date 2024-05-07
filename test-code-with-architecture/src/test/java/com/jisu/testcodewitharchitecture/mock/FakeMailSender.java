package com.jisu.testcodewitharchitecture.mock;

import com.jisu.testcodewitharchitecture.user.service.port.MailSender;

public class FakeMailSender implements MailSender {

    private String email;
    private String title;
    private String content;

    @Override
    public void send(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
