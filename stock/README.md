# 테스트 코드
```java
@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void after() {
        stockRepository.deleteAll();
    }

    @Test
    void stock_decrease() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findById(1L).get();

        assertEquals(99L, stock.getQuantity());
    }

    @Test
    void 동시에_100개_요청() {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertThat(stock.getQuantity()).isEqualTo(0L);
    }
}
```

# synchronized 로 동시성 제어
```java
@Transactional
public synchronized void decrease(Long id, Long quantity) {

    Stock stock = stockRepository.findById(id).orElseThrow();

    stock.decrease(quantity);

    stockRepository.saveAndFlush(stock);
}
```
위의 코드를 테스트하면 실패하게 된다. 왜나햐만 트랜잭션으로 인해 트랜잭션이 끝나기 전에 다른 쓰레드들이 접근하기 때문에 테스트가 깨지게 된다.

# @Lock 을 이용한 동시성 제어
## Pessimistic Lock
실제로 데이터에 Lock을 걸어서 정합성을 맞추는 방법이다.  exclusive lock을 걸게되면 다른 트랜잭션에서는 
lock이 해제되기전에 데이터를 가져갈 수 없게된다. 데드락이 걸릴 수 있기땜누에 주의하여 사용해야한다.

## Optimistic Lock
실제로 Lock을 이요하지 않고 버전을 이용함으로써 정합성을 맞추는 방법이다. 먼저 데이터를 읽은 후에 update를 수행할 때 현재 내가 읽은 버전이 맞는지 확인하여
업데이트 한다. 내가 읽은 버전에서 수정사항이 생겼을 경우에는 application에서 다시 읽은 후에 작업을 수행해야한다.

## Named Lock
이름을 가진 metadata locking이다. 이름을 가진 락을 획득한 후 해제될때까지 다른 세션은 이 락을 획득할 수 없도록 한다.  
주의할점으로는 transaction이 종료될 때 lock이 자동으로 해제되지 않는다. 별도의 명령어로 해제를 수행해주거나 선점시간이 끝나야 해제된다.