# axon-server
Makefile 파일이 있는 위치에서 다음 명령어를 실행하면 axon-server를 빌드할 수 있다.
```bash
make
```

# axon-server 흐름
1. 서비스에서 axon-server 의존성을 통해  commandGateway 주입 받는다.
2. commandGateway 를 통해 command 를 보낸다. -> 이벤트를 발행한다.
    1. Money-service -> application.service.IncreaseMoneyRequestService 
```java
@Service
public class IncreaseMoneyRequestService {
    private final CommandGateway commandGateway;

    public IncreaseMoneyRequestService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        CreateMoneyCommand axonCommand = new CreateMoneyCommand(command.getMembershipId());
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.out.println("throwable = " + throwable);
            } else {
                createMemberPort.createMember(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString()));
                System.out.println("result = " + result);
            }
        });
    }
}
    ```

3. 이벤트를 받아서 처리하는 핸들러를 구현한다.
```java
@CommandHandler
public MemberMoneyAggregate(@NotNull CreateMoneyCommand command) {
    System.out.println("CreateMoneyCommand Handler");
    // store event
    apply(new MemberMoneyCreateEvent(command.getMembershipId()));
}
```
4. 이벤트를 다시 발행한곳으로 소싱하기 위한 핸들러를 구현한다.
```java
@EventSourcingHandler
public void on(MemberMoneyCreateEvent event) {
    System.out.println("MemberMoneyCreateEvent Sourcing Handler");
    id = UUID.randomUUID().toString();
    membershipId = Long.parseLong(event.getMembershipId());
    balance = 0;
}
```