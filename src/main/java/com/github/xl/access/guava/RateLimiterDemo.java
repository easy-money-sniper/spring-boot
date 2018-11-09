package com.github.xl.access.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/07 18:29
 * refer to: http://xiaobaoqiu.github.io/blog/2015/07/02/ratelimiter
 * refer to: https://segmentfault.com/a/1190000016182737
 */
public class RateLimiterDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterDemo.class);

    public static void main(String[] args) throws InterruptedException {
        RateLimiter smoothWarmingUp = RateLimiter.create(10.0, 3, TimeUnit.SECONDS);
        RateLimiter smoothBursty = RateLimiter.create(10.0);
        Thread.sleep(1000);
        long start = Instant.now().toEpochMilli();
        for (int i = 0; i < 50; i++) {
            long s = Instant.now().toEpochMilli();
            smoothBursty.acquire();
            LOGGER.info("call execute.." + i + " " + (Instant.now().toEpochMilli() - s));
            if (i == 12) {
                // 休眠一秒。则令牌桶已满，后10个请求不用等待直接获取。
                Thread.sleep(1000);
            }
        }
        LOGGER.info("========={}=========", Instant.now().toEpochMilli() - start);

        for (int i = 0; i < 30; i++) {
            long s = Instant.now().toEpochMilli();
            smoothWarmingUp.acquire();
            LOGGER.info("call execute.." + i + " " + (Instant.now().toEpochMilli() - s));
        }
    }
}
