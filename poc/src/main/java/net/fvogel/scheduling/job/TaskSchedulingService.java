package net.fvogel.scheduling.job;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class TaskSchedulingService {

    @Autowired
    Scheduler scheduler;

    public <T extends Task> void schedule(Class<T> taskClass, Map<String, Object> parameters) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(taskClass)
                .withIdentity(taskClass.getSimpleName() + ":" + UUID.randomUUID().toString())
                .build();
        jobDetail.getJobDataMap().putAll(parameters);

        Trigger trigger = newTrigger()
                        .withIdentity(taskClass.getSimpleName() + ":" + UUID.randomUUID().toString())
                .startAt(new Date(new Date().getTime() + 5000))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
