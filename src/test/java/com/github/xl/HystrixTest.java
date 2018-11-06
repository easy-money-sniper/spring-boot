package com.github.xl;

import com.github.xl.access.hystrix.HystrixHandler;
import com.netflix.hystrix.HystrixCommand;
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
        for (int i = 0; i < 120; i++) {
            TimeUnit.MILLISECONDS.sleep(500);
            HystrixCommand<String> hystrixCommand = new HystrixHandler();
            String res = hystrixCommand.execute();
            LOGGER.info("call times: " + (i + 1)
                    + ", result: " + res
                    + ", isOpen: " + hystrixCommand.isCircuitBreakerOpen()
                    + ", percent: " + hystrixCommand.getMetrics().getHealthCounts().getErrorPercentage());
        }
    }
}
