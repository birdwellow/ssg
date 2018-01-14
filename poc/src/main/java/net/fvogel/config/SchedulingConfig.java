package net.fvogel.config;

import net.fvogel.scheduling.job.ResourcesUpdateJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class SchedulingConfig {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler schedulerFactoryBean(JobFactory jobFactory,
            @Qualifier("continuousJobTrigger") Trigger jobTrigger) throws Exception {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.afterPropertiesSet();

        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.scheduleJob((JobDetail) jobTrigger.getJobDataMap().get("jobDetail"), jobTrigger);

        scheduler.start();
        return scheduler;
    }

    @Bean(name = "continuousJobTrigger")
    public SimpleTriggerFactoryBean continuousJobTrigger(@Qualifier("resourceJobDetail") JobDetail jobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(5000);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    @Bean
    @Qualifier("resourceJobDetail")
    public JobDetailFactoryBean resourceJobDetailFactoryBean() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ResourcesUpdateJob.class);
        return factoryBean;
    }

}
