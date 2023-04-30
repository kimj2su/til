package hello.proxy.pureproxy.blogcode.code;

public class ProxyFeed implements Feed {

    private String name;
    private Feed feed;

    public ProxyFeed(Feed feed, String name) {
        this.feed = feed;
        this.name = name;
    }

    @Override
    public String load() {
        System.out.println("프록시로 사용합니다.");
        return feed.load();
    }
}
