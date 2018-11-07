package com.github.xl.access.hystrix;

import com.netflix.hystrix.*;

import java.util.Random;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/02 17:08
 * refer to: https://www.jianshu.com/p/14958039fd15
 * refer to: https://github.com/Netflix/Hystrix/wiki/How-it-Works
 */
public class HystrixHandler extends HystrixCommand<String> {
    // enable request cache
    @Override
    protected String getCacheKey() {
        return commandKey.name();
    }

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
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD) // 隔离策略
                        .withExecutionTimeoutInMilliseconds(1000) // 执行超时（只有调用get才生效）
                        .withExecutionTimeoutEnabled(true)
                        .withExecutionIsolationThreadInterruptOnTimeout(true) // 超时是否中断线程
                        .withExecutionIsolationThreadInterruptOnFutureCancel(false)
                        // fallback
                        .withFallbackEnabled(true)
                        // circuit breaker
                        .withCircuitBreakerEnabled(true)
                        .withCircuitBreakerRequestVolumeThreshold(20) // 达到时间窗口内的最小请求数量
                        .withCircuitBreakerSleepWindowInMilliseconds(5000) // 熔断开启到尝试半开的时间
                        .withCircuitBreakerErrorThresholdPercentage(50) // 错误百分比（及最小请求量同时达到），才触发熔断机制
                        .withCircuitBreakerForceOpen(false)
                        .withCircuitBreakerForceClosed(false)
                        // metrics
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000) // 时间窗口
                        .withMetricsRollingStatisticalWindowBuckets(10) // 时间窗口拆分的bucket（默认每秒一个bucket）
                        .withMetricsRollingPercentileEnabled(true)
                        .withMetricsRollingPercentileWindowInMilliseconds(60000)
                        .withMetricsRollingPercentileWindowBuckets(6)
                        .withMetricsRollingPercentileBucketSize(100)
                        .withMetricsHealthSnapshotIntervalInMilliseconds(500)
                        // request cache
                        .withRequestCacheEnabled(true) // 缓存
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
