package com.lwd.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @author lwd
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    private static final int CPU_CORES = Runtime.getRuntime().availableProcessors();

    /** CPU密集型 */
    @Bean("cpuExecutor")
    public Executor cpuExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CPU_CORES);
        executor.setMaxPoolSize(CPU_CORES);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("cpu-executor-");
        executor.setKeepAliveSeconds((int) TimeUnit.SECONDS.toSeconds(10));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    /** IO密集型 */
    @Bean("ioExecutor")
    public Executor ioExecutor() {
        ThreadFactory factory = Thread.ofVirtual().name("io-executor-", 0).factory();
        return Executors.newThreadPerTaskExecutor(factory);
    }
}
