package com.github.xl.access.hystrix;

import com.netflix.hystrix.*;

import java.util.Random;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/02 17:08
 * refer to: https://www.jianshu.com/p/14958039fd15
 * refer to: https://github.com/Netflix/Hystrix/wiki/How-it-Works
 * refer to: http://zyouwei.com/%E6%8A%80%E6%9C%AF%E7%AC%94%E8%AE%B0/Java/Hystrix-configuration.html
 * refer to: http://www.10tiao.com/html/164/201704/2652898424/1.html
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
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD) // 隔离策略
                        .withExecutionTimeoutInMilliseconds(1000) // 执行超时（只有调用get才生效）
                        .withExecutionTimeoutEnabled(true)
                        .withExecutionIsolationThreadInterruptOnTimeout(true) // 超时是否中断线程
                        .withExecutionIsolationThreadInterruptOnFutureCancel(false) // 超时是否中断线程 Future.cancel()
                        // fallback
                        .withFallbackEnabled(true)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(10) // Number of permits for fallback semaphore，若fallback最大并发超过配置，则会快速失败异常
                        // circuit breaker
                        .withCircuitBreakerEnabled(true)
                        .withCircuitBreakerRequestVolumeThreshold(20) // 时间窗口内的最小请求数量
                        .withCircuitBreakerSleepWindowInMilliseconds(5000) // 熔断后的重试时间窗口
                        .withCircuitBreakerErrorThresholdPercentage(50) // 错误百分比（及最小请求量同时达到），才触发熔断机制
                        .withCircuitBreakerForceOpen(false)
                        .withCircuitBreakerForceClosed(false)
                        // metrics
                        // 计数/最大并发采样统计
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000) // 保持断路器使用和发布指标的时间窗口
                        .withMetricsRollingStatisticalWindowBuckets(10) // 时间窗口拆分的bucket（默认每秒一个bucket）
                        .withMetricsRollingPercentileEnabled(true)
                        // Command时延分布采样统计
                        .withMetricsRollingPercentileWindowInMilliseconds(60000) // 保留执行时间以允许百分数计算
                        .withMetricsRollingPercentileWindowBuckets(6)
                        .withMetricsRollingPercentileBucketSize(100)
                        .withMetricsHealthSnapshotIntervalInMilliseconds(500) // 计算健康度的频率
                        // request cache
                        .withRequestCacheEnabled(true) // 请求缓存
                        .withRequestLogEnabled(true)) // 执行和事件记录
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaximumSize(10)
                        .withMaxQueueSize(-1) // 默认-1，使用SynchronousQueue
                        .withQueueSizeRejectionThreshold(5) // maxQueueSize -1时不生效
                        .withKeepAliveTimeMinutes(1)
                        .withAllowMaximumSizeToDivergeFromCoreSize(false) // 默认maximum==core，若true，则maximum设置生效
                        // 为线程池保留多长时间的指标
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                        .withMetricsRollingStatisticalWindowBuckets(10));
    }

    // enable request cache
    @Override
    protected String getCacheKey() {
        return commandKey.name();
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
