package com.github.xl;

import com.github.xl.access.hystrix.HystrixHandler;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/06 15:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HystrixTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixTest.class);

    @Test
    public void testCircuitBreaker() throws InterruptedException {
        new Thread(new Worker("Kevin")).start();
        new Thread(new Worker("Liang")).start();
        Thread.sleep(90 * 1000);
    }

    @Test
    public void testRequestCache() {
        // need to initialize context if want to use request cache/log/collapsing
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            HystrixCommand<String> commandA = new HystrixHandler("Thunder", "a");
            HystrixCommand<String> commandB = new HystrixHandler("Thunder", "a");
            LOGGER.info(commandA.execute());
            LOGGER.info(commandB.execute());
            LOGGER.info(commandA.isResponseFromCache() + ":" + commandB.isResponseFromCache());
        } finally {
            context.shutdown();
        }

    }

    class Worker implements Runnable {
        private final String commandKey;

        Worker(String commandKey) {
            this.commandKey = commandKey;
        }

        @Override
        public void run() {
            for (int i = 0; i < 120; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    LOGGER.error("sleep interrupted...");
                }
                HystrixCommand<String> hystrixCommand = new HystrixHandler("Thunder", commandKey);
                String res = hystrixCommand.execute();
                LOGGER.info(hystrixCommand.getCommandKey().name() + " call times: " + (i + 1)
                        + ", result: " + res
                        + ", isOpen: " + hystrixCommand.isCircuitBreakerOpen()
                        + ", percent: " + hystrixCommand.getMetrics().getHealthCounts().getErrorPercentage());
            }
        }
    }
}
