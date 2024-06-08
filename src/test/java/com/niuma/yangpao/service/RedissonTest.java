package com.niuma.yangpao.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void testWatchDog() {
        RLock lock = redissonClient.getLock("yangpao:precachejob:docache:lock");

        try {
            // 只有一个线程可以获得锁
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                Thread.sleep(3000000);
                System.out.println("getLock: " + Thread.currentThread().getId());
            }
        } catch (Exception e) {
            log.info("doCacheRecommendUser error", e);
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                System.out.println("unLock: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}
