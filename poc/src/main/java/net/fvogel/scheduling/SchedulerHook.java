package net.fvogel.scheduling;

import java.util.List;

import javax.annotation.PostConstruct;

import net.fvogel.scheduling.job.ResourcesUpdateJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;


@Component
public class SchedulerHook {

    @PostConstruct
    public void startSchedules() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduleResourcesUpdateJob(scheduler);

        scheduler.start();
    }

    private void scheduleResourcesUpdateJob(Scheduler scheduler) throws SchedulerException {

        JobDetail job = newJob(ResourcesUpdateJob.class)
                .withIdentity("resource-update-job", "cron-job-group")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("resource-update-trigger", "cron-trigger-group")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);

    }


}
