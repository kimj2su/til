package hello.proxy.pureproxy.blogcode.code;

import java.util.List;

public class FeedList {

    private List<Feed> feeds;

    public FeedList(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public void onScroll(final int start, final int end) {
        for (int i = start; i <= end; i++) {
            final Feed feed = feeds.get(i);
            feed.load();
        }
    }
}
