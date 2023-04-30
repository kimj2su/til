package hello.proxy.pureproxy.blogcode;

import hello.proxy.pureproxy.blogcode.code.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxyTest {

    @Test
    void proxyPattern() {
        final List<String> names = Arrays.asList("feed1", "feed2", "feed3", "feed4", "feed5", "feed6");
        final List<Feed> feeds = new ArrayList<>(names.size());

        for (int i = 0; i < names.size(); i++) {
            if (i <= 3) {
                feeds.add(new RealFeed(names.get(i)));
                continue;
            }
            feeds.add(new TimeDecorator(new ProxyFeed(new RealFeed(names.get(i)), names.get(i))));
//            feeds.add(new ProxyFeed(new RealFeed(names.get(i)), names.get(i)));
        }

        final FeedList feedList = new FeedList(feeds);
        feedList.onScroll(0, 3);
        System.out.println();
        feedList.onScroll(4, 5);
    }
}
