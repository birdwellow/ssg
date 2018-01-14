package net.fvogel.scheduling.job;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.Job;
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

    public <T extends Task> TaskBuilder plan(Class<T> taskClass) throws SchedulerException {
        return new TaskBuilder(taskClass);
    }

    public class TaskBuilder<T extends Task> {

        private Class<T> taskClass;
        private Map<String, Object> parameters = new HashMap();
        private Date date;

        public TaskBuilder(Class <T> taskClass) {
            this.taskClass = taskClass;
        }

        public TaskBuilder withParameter(Object parameter) {
            if (parameter != null) {
                this.withParameter(parameter.getClass().getSimpleName(), parameter);
            }
            return this;
        }

        public TaskBuilder withParameter(String name, Object parameter) {
            if (parameter != null) {
                this.parameters.put(name, parameter);
            }
            return this;
        }

        public TaskBuilder withParameters(Map<String, Object> parameters) {
            if (parameters != null) {
                this.parameters.putAll(parameters);
            }
            return this;
        }

        public TaskBuilder withParameters(List<Object> parameters) {
            if (parameters != null) {
                for (Object parameter : parameters) {
                    this.withParameter(parameter);
                }
            }
            return this;
        }

        public TaskBuilder withParameters(Object... parameters) {
            if (parameters != null) {
                this.withParameters(Arrays.asList(parameters));
            }
            return this;
        }

        public TaskBuilder atDate(Date date) {
            if (date != null) {
                this.date = date;
            }
            return this;
        }

        public TaskBuilder withDelayMilliseconds(long milliseconds) {
            if (milliseconds > 0) {
                this.date = new Date(new Date().getTime() + milliseconds);
            }
            return this;
        }

        public void fire() throws SchedulerException {

            if (parameters == null) {
                throw new SchedulerException("Task parameters are null");
            } else if (date == null) {
                throw new SchedulerException("Task execution date null");
            } else if (date.before(new Date())) {
                throw new SchedulerException("Task execution date is in the past");
            }

            JobDetail jobDetail = JobBuilder.newJob()
                    .ofType(taskClass)
                    .withIdentity(taskClass.getSimpleName() + ":" + UUID.randomUUID().toString())
                    .build();
            jobDetail.getJobDataMap().putAll(parameters);

            Trigger trigger = newTrigger()
                    .withIdentity(taskClass.getSimpleName() + ":" + UUID.randomUUID().toString())
                    .startAt(date)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }

    }

}
