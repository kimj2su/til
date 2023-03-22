package com.example.redis.event.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MyRedis myRedis;

    @Override
    public boolean temp() {
        String lock = "lock_key"; // 해당 키에만 lock을 걸고 싶으면 value를 key로
        boolean ret = false;
        RTransaction transaction = myRedis.startRedisTransacton();
        TransactionStatus status = myRedis.startDBTransacton();
        try {
            if (myRedis.tryLock(lock, TimeUnit.SECONDS, 5L, 10L)) {
                // Redis 처리
                RBucket<int[]> imageBucket = transaction.getBucket("key");
                int[] imageList = imageBucket.get();

                /* redis test
                if(true)
                {
                	throw new Exception()
                }
                */

                imageList[3]++;
                imageBucket.set(imageList);

                /* redis test2
                if(true)
                {
                	throw new Exception()
                }
                */

                RBucket<Integer> memberBucket = transaction.getBucket("key");
                int savedBitmap = memberBucket.get();
                memberBucket.set(++savedBitmap);

                /*
                DB 처리
                */

                /* db,redis rollback test
                if(true)
                {
                	throw new Exception()
                }
                */

                myRedis.commitRedis(transaction);
                myRedis.commitDB(status);
            }
        } catch (Exception e) {
            myRedis.rollbackRedis(transaction);
            myRedis.rollbackDB(status);
            e.getStackTrace();
            ret = false;
        } finally {
            if (myRedis.canUnlock(lock)) {
                myRedis.unlock(lock);
            }
        }
        return ret;
    }
}