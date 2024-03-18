package com.example.application.service;

import com.example.adapter.axon.command.CreateMoneyCommand;
import com.example.adapter.out.persistance.MemberMoneyJpaEntity;
import com.example.adapter.out.persistance.MoneyChangingRequestMapper;
import com.example.application.port.in.CreateMemberMoneyCommand;
import com.example.application.port.in.CreateMemberMoneyUseCase;
import com.example.application.port.in.IncreaseMoneyRequestCommand;
import com.example.application.port.in.IncreaseMoneyRequestUseCase;
import com.example.application.port.out.GetMembershipPort;
import com.example.application.port.out.IncreaseMoneyPort;
import com.example.application.port.out.SendRechargingMoneyTaskPort;
import com.example.common.CountDownLatchManager;
import com.example.common.RechargingMoneyTask;
import com.example.common.SubTask;
import com.example.common.UseCase;
import com.example.domain.MemberMoney;
import com.example.domain.MoneyChangingRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final GetMembershipPort getMembershipPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;
    private final CommandGateway commandGateway;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 머니의 충전.증액이라는 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)
        getMembershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액이 필요해요
        // 6-2. 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴
        return getMoneyChangingRequest(command);
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {
        // Count증가
        countDownLatchManager.addCountDownLatch("rechargingMoneyTask");
        
        // 1. Subtask, Task
        SubTask validateMembershipIdTask = SubTask.builder()
                .subTaskName("validMemberTask: " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // banking 유효성 검사
        SubTask validateBankingTask = SubTask.builder()
                .subTaskName("validateBanking")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        // Amount Money Firmbanking --> 무조건 ok 받았다고 가정
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validateMembershipIdTask);
        subTaskList.add(validateBankingTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("rechargingMoneyTask")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("kimjisu") // 법인 계좌 은행 이름
                .build();

        // 2. Kafka Cluster Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);

        // 3. Wait
        // block, wait....
        try {
            // Task 완료 이벤트 올 때까지 기다린다.
            countDownLatchManager.getCountDownLatch("rechargingMoneyTask").await();
            String result = countDownLatchManager.getDataForKey(task.getTaskID());
            if (result.equals("success")) {
                System.out.println("success for async Money Recharging!!");
                return getMoneyChangingRequest(command);
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            // 문제 발생 시 핸들링.
            throw new RuntimeException(e);
        }
    }

    @Nullable
    private MoneyChangingRequest getMoneyChangingRequest(IncreaseMoneyRequestCommand command) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
                ,command.getAmount());

        if(memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.MoneyChangingType(1),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.MoneyChangingStatus(1),
                            new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                    )
            );
        }

        return null;
    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        CreateMoneyCommand axonCommand = new CreateMoneyCommand(command.getMembershipId());
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.out.println("throwable = " + throwable);
            } else {
                System.out.println("result = " + result);
            }
        });
    }
}
