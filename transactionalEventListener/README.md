
# @EventListener
Event를 사용할 때 기본적으로 사용하는 @EventListener는 event를 publishing 하는 코드 시점에 바로 publishing한다.  
event를 퍼블리싱 할때는 대부분 메인 작업이 아닌 서브의 작업이 많고 비동기로 진행해도 되는 경우도 많다.
이때 1, 2, 3번 의 순서로 진행 될때 3번에서 예외처리가 났을때 후처리하기가 까다롭다.
이러한 문제를 해결하기 위해서 @TransactionEventListener가 나왔다.  
@TransactionEventListener는 Event의 실질적인 발생을 트랜잭션의 종료를 기준으로 삼는것입니다.

# @TransactionalEventListener 옵션  
@TransactionalEventListener을 이용하면 트랜잭션의 어떤 타이밍에 이벤트를 발생시킬 지 정할 수 있다.  
옵션을 사용하는 방법은 Transaction Phase을 이용하는 것이며 아래와 같은 옵션을 사용할 수 있습니다.  


```java
@TransactionalEventListener
@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
```

- AFTER_COMMIT (기본값) - 트랜잭션이 성공적으로 마무리(commit)됬을 때 이벤트 실행 
- BEFORE_COMMIT - 트랜잭션의 커밋 전에 이벤트 실행
- AFTER_COMPLETION – 트랜잭션이 마무리 됬을 때(commit or rollback) 이벤트 실행 
- AFTER_ROLLBACK – 트랜잭션이 rollback 됬을 때 이벤트 실행 

## 예제 코드

### UserService
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;
    public User createUser(String userName) {

        User user = User.builder()
                .userName(userName)
                .status(INACTIVE)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User modifyUserStatusWithTransactional(Long id, UserStatus status, Boolean isException) {
        log.info("메서드 시작");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));

        publisher.publishEvent(new UserEvent(status, user));
        log.info("이벤트 발생 요청");

        if (isException) {
            throw new IllegalArgumentException("예외 발생");
        }

        log.info("메서드 종료");
        return user;
    }

    public User modifyUserStatusWithOutTransactional(Long id, UserStatus status) {
        log.info("메서드 시작");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));

        publisher.publishEvent(new UserEvent(status, user));
        log.info("이벤트 발생 요청");

        log.info("메서드 종료");
        return user;
    }
}
```
### UserEvent
```java
@Getter
public class UserEvent {

    private UserStatus status;
    private User user;

    public UserEvent(UserStatus status, User user) {
        this.status = status;
        this.user = user;
    }
}
```

### UserEventListener

```java
@Slf4j
@Component
public class UserEventListener {

    @TransactionalEventListener
//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void modifyUserStatus(UserEvent event) throws InterruptedException {
        log.info("요청 유저 상태: {}", event.getStatus());
        User user = event.getUser();
        log.info("수정전 유저 상태: {}", user.getStatus());
        user.modifyStatus(event.getStatus());
        log.info("수정된 유저 상태: {}", user.getStatus());
    }
}
```

### UserServiceTest
```java
@SpringBootTest
@RecordApplicationEvents
class UserServiceTest {
    @Autowired
    private UserService userService;

    @DisplayName("유저를 생성한다.")
    @Test
    void createUser() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);
    }

    @DisplayName("트랜잭션 어노테이션과 이벤트를 발행하여 유저 상태를 ACTIVE로 변경한다.")
    @Test
    void modifyUserStatusWithTransactional() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);

        //@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, AFTER_COMPLETION, AFTER_COMMIT)
        User modifiedUser = userService.modifyUserStatusWithTransactional(user.getId(), ACTIVE, false);
        assertThat(modifiedUser.getUserName()).isEqualTo(username);
        assertThat(modifiedUser.getStatus()).isEqualTo(ACTIVE);
    }

    @DisplayName("예외가 발생하면 이벤트가 발생하지 않는다.")
    @Test
    void modifyUserStatusWithTransactionalThrowsException() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);

        // @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
        assertThatThrownBy(() -> userService.modifyUserStatusWithTransactional(user.getId(), ACTIVE, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예외 발생");
    }

    @DisplayName("트랜잭션 어노테이션없이 이벤트를 사용하면 이벤트가 발행되지 않아 유저 상태가 변하지 않는다.")
    @Test
    void modifyUserStatusWithOutTransactional() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);

        User modifiedUser = userService.modifyUserStatusWithOutTransactional(user.getId(), ACTIVE);
        assertThat(modifiedUser.getUserName()).isEqualTo(username);
        assertThat(modifiedUser.getStatus()).isEqualTo(INACTIVE);
    }

}
```


## AFTER_COMMIT (기본값)
이벤트는 커밋이 된 후에 실행된다.
```java
2023-05-17T16:31:32.287+09:00  INFO 28936 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 시작
2023-05-17T16:31:32.319+09:00  INFO 28936 --- [    Test worker] c.e.springevent.domain.user.UserService  : 이벤트 발생 요청
2023-05-17T16:31:32.324+09:00  INFO 28936 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 종료
2023-05-17T16:36:29.122+09:00  INFO 25188 --- [    Test worker] c.e.springevent.event.UserEventListener  : 요청 유저 상태: ACTIVE
2023-05-17T16:36:29.122+09:00  INFO 25188 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정전 유저 상태: INACTIVE
2023-05-17T16:36:29.122+09:00  INFO 25188 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정된 유저 상태: ACTIVE
```

## BEFORE_COMMIT - 트랜잭션의 커밋 전에 이벤트 실행
커밋이 되기전 이벤트가 실행 된다.
```java
2023-05-17T16:37:17.590+09:00  INFO 20928 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 시작
2023-05-17T16:37:17.621+09:00  INFO 20928 --- [    Test worker] c.e.springevent.domain.user.UserService  : 이벤트 발생 요청
2023-05-17T16:37:17.622+09:00  INFO 20928 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 종료
2023-05-17T16:37:17.623+09:00  INFO 20928 --- [    Test worker] c.e.springevent.event.UserEventListener  : 요청 유저 상태: ACTIVE
2023-05-17T16:37:17.623+09:00  INFO 20928 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정전 유저 상태: INACTIVE
2023-05-17T16:37:17.623+09:00  INFO 20928 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정된 유저 상태: ACTIVE
2023-05-17T16:37:17.623+09:00 DEBUG 20928 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
2023-05-17T16:37:17.624+09:00 DEBUG 20928 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(1782724863<open>)]
```

## AFTER_COMPLETION – 트랜잭션이 마무리 됬을 때(commit or rollback) 이벤트 실행
```java

2023-05-17T16:40:46.552+09:00  INFO 25692 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 시작
2023-05-17T16:40:46.587+09:00  INFO 25692 --- [    Test worker] c.e.springevent.domain.user.UserService  : 이벤트 발생 요청
2023-05-17T16:40:46.588+09:00  INFO 25692 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 종료
2023-05-17T16:40:46.588+09:00 DEBUG 25692 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
2023-05-17T16:40:46.589+09:00 DEBUG 25692 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(1782724863<open>)]
2023-05-17T16:40:46.590+09:00  INFO 25692 --- [    Test worker] c.e.springevent.event.UserEventListener  : 요청 유저 상태: ACTIVE
2023-05-17T16:40:46.590+09:00  INFO 25692 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정전 유저 상태: INACTIVE
2023-05-17T16:40:46.590+09:00  INFO 25692 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정된 유저 상태: ACTIVE
```

## AFTER_ROLLBACK – 트랜잭션이 rollback 됬을 때 이벤트 실행
기본적으로 예외가 발생하면 이벤트가 실행안된다. 하지만 AFTER_ROLLBACK으로 설정했을 경우 이벤트가 발생한다.
```java
2023-05-17T16:50:40.955+09:00  INFO 19572 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 시작
2023-05-17T16:50:40.984+09:00  INFO 19572 --- [    Test worker] c.e.springevent.domain.user.UserService  : 이벤트 발생 요청
2023-05-17T16:50:40.986+09:00 DEBUG 19572 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction rollback
2023-05-17T16:50:40.986+09:00 DEBUG 19572 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Rolling back JPA transaction on EntityManager [SessionImpl(626754729<open>)]
2023-05-17T16:50:40.987+09:00  INFO 19572 --- [    Test worker] c.e.springevent.event.UserEventListener  : 요청 유저 상태: ACTIVE
2023-05-17T16:50:40.987+09:00  INFO 19572 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정전 유저 상태: INACTIVE
2023-05-17T16:50:40.987+09:00  INFO 19572 --- [    Test worker] c.e.springevent.event.UserEventListener  : 수정된 유저 상태: ACTIVE
```

## 트랜 잭션 어노테이션이 없으면 이벤트는 실행이 안된다.
```java
2023-05-17T16:54:14.036+09:00  INFO 30300 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 시작
2023-05-17T16:54:14.094+09:00 DEBUG 30300 --- [    Test worker] actionalApplicationListenerMethodAdapter : No transaction is active - skipping org.springframework.context.PayloadApplicationEvent[source=org.springframework.web.context.support.GenericWebApplicationContext@1eb6e1c, started on Wed May 17 16:54:10 KST 2023]
2023-05-17T16:54:14.094+09:00  INFO 30300 --- [    Test worker] c.e.springevent.domain.user.UserService  : 이벤트 발생 요청
2023-05-17T16:54:14.094+09:00  INFO 30300 --- [    Test worker] c.e.springevent.domain.user.UserService  : 메서드 종료
```