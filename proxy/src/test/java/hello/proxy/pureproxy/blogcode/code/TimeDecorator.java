package hello.proxy.pureproxy.blogcode.code;

public class TimeDecorator implements Feed {

    private Feed feed;

    public TimeDecorator(Feed feed) {
        this.feed = feed;
    }

    @Override
    public String load() {
        long startTime = System.currentTimeMillis();

        String result = feed.load();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("피드 로딩 종료 resultTime=" + resultTime + "ms, feedName=" + result );

        return result;
    }
}
