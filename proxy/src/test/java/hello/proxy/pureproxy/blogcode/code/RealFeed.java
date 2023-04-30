package hello.proxy.pureproxy.blogcode.code;

import hello.proxy.pureproxy.blogcode.code.Feed;

public class RealFeed implements Feed {

    private final String name;

    public RealFeed(String name) {
        this.name = name;
    }

    @Override
    public String load() {

        System.out.println(this.name + " 피드를 로딩합니다.");
        return this.name;
    }
}
