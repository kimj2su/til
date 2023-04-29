package com.example.threadlocal.trace.strategy;

import com.example.threadlocal.trace.strategy.code.strategy.ContextV1;
import com.example.threadlocal.trace.strategy.code.strategy.Strategy;
import com.example.threadlocal.trace.strategy.code.strategy.StrategyLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직2 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        StrategyLogic1 strategyLogic2 = new StrategyLogic1();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategy1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();
        log.info("strategyLogic1={}", strategy1.getClass());

        Strategy strategy2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategy2);
        context2.execute();
        log.info("strategyLogic2={}", strategy2.getClass());
    }

    @Test
    void strategyV3() {

        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context1.execute();
        log.info("strategyLogic1={}", new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        }.getClass());

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context2.execute();
        log.info("strategyLogic2={}", new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        }.getClass());
    }

    @Test
    void strategyV4() {

        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        context2.execute();
    }


}
