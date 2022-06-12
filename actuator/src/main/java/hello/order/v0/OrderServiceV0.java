package hello.order.v0;

import hello.order.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV0 implements OrderService {

    private final AtomicInteger stock = new AtomicInteger(100);

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet(); // 값이 줄어든다.
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet(); // 값이 늘어남.
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
