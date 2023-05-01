package codewifi.job;


import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
public abstract class JobBase implements SchedulingConfigurer {

	private  ThreadPoolTaskScheduler taskScheduler;

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		MDC.put("traceId", String.valueOf(System.currentTimeMillis()));
		scheduledTaskRegistrar.setScheduler(taskScheduler);
		scheduledTaskRegistrar.addTriggerTask(
				// 执行定时任务
				this::taskService,
				// 设置触发器
				triggerContext -> {
					CronTrigger trigger = new CronTrigger(getCron());
					return trigger.nextExecutionTime(triggerContext);
				});
	}

	public abstract void taskService();

	public abstract String getCron();

}
