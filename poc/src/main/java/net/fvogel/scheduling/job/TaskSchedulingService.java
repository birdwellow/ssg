package net.fvogel.scheduling.job;

import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class TaskSchedulingService {

    @Autowired
    Scheduler scheduler;

    @Autowired
    @Qualifier("jobDetailFactoryBean")
    JobDetailFactoryBean jobDetailFactoryBean;

    public void schedule(Task task) throws SchedulerException {
        JobDetail jobDetail = builder()
                .ofType(task.getJobClass())
                .withIdentity(task.getJobClass().getSimpleName() + ":" + UUID.randomUUID().toString())
                .build();
        jobDetail.getJobDataMap().putAll(task.getParameters());

        Trigger trigger = newTrigger()
                        .withIdentity(task.getJobClass().getSimpleName() + ":" + UUID.randomUUID().toString())
                .startAt(new Date(new Date().getTime() + 5000))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobBuilder builder() {
        return jobDetailFactoryBean.getObject().getJobBuilder();
    }

}
