package codewifi.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

	private final TaskExecutionProperties taskExecutionProperties;

	public ExecutorConfig(TaskExecutionProperties taskExecutionProperties) {
		this.taskExecutionProperties = taskExecutionProperties;
	}

	@Bean
	public Executor executor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

		// 配置核心线程数
		threadPoolTaskExecutor.setCorePoolSize(taskExecutionProperties.getPool().getCoreSize());
		// 配置最大线程数
		threadPoolTaskExecutor.setMaxPoolSize(taskExecutionProperties.getPool().getMaxSize());
		// 配置队列大小
		threadPoolTaskExecutor.setQueueCapacity(taskExecutionProperties.getPool().getQueueCapacity());
		// 配置线程池中的线程的名称前缀
		threadPoolTaskExecutor.setThreadNamePrefix(taskExecutionProperties.getThreadNamePrefix());

		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);

		// 执行初始化
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

	/**
	 * 定时任务调度器线程池
	 * @param builder
	 * @return
	 */
	@Bean
	public ThreadPoolTaskScheduler taskScheduler(TaskSchedulerBuilder builder) {
		return builder.build();
	}

}
