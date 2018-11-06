package com.github.xl.access.hystrix;

import com.netflix.hystrix.*;

import java.util.Random;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/02 17:08
 * refer to: https://www.jianshu.com/p/14958039fd15
 * refer to: https://github.com/Netflix/Hystrix/wiki/How-it-Works
 */
public class HystrixHandler extends HystrixCommand<String> {

    public HystrixHandler(final String groupKey, final String commandKey) {
        super(setter(groupKey, commandKey));
    }

    private static Setter setter(final String groupKey, final String commandKey) {
        return HystrixCommand.Setter
                // group key
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                // command key
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                // command props
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // execution
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        .withExecutionTimeoutInMilliseconds(1000)
                        .withExecutionTimeoutEnabled(true)
                        .withExecutionIsolationThreadInterruptOnTimeout(true)
                        .withExecutionIsolationThreadInterruptOnFutureCancel(false)
                        // fallback
                        .withFallbackEnabled(true)
                        // circuit breaker
                        .withCircuitBreakerEnabled(true)
                        .withCircuitBreakerRequestVolumeThreshold(20)
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        .withCircuitBreakerForceOpen(false)
                        .withCircuitBreakerForceClosed(false)
                        // metrics
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                        .withMetricsRollingStatisticalWindowBuckets(10)
                        .withMetricsRollingPercentileEnabled(true)
                        .withMetricsRollingPercentileWindowInMilliseconds(60000)
                        .withMetricsRollingPercentileWindowBuckets(6)
                        .withMetricsRollingPercentileBucketSize(100)
                        .withMetricsHealthSnapshotIntervalInMilliseconds(500)
                        // request cache
                        .withRequestCacheEnabled(true)
                        .withRequestLogEnabled(true))
                // thread pool
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaximumSize(10)
                        .withMaxQueueSize(-1)
                        .withQueueSizeRejectionThreshold(5)
                        .withKeepAliveTimeMinutes(1)
                        .withAllowMaximumSizeToDivergeFromCoreSize(false)
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                        .withMetricsRollingStatisticalWindowBuckets(10));
    }

    @Override
    protected String run() throws Exception {
        Random random = new Random();
        if ((random.nextInt() & 1) == 0) {
            throw new Exception();
        }
        return "running";
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }
}
