package net.fvogel.scheduling.job;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class Task implements Job {

    public final void execute(JobExecutionContext context) throws JobExecutionException {
        List<Field> fields = Arrays.asList(getClass().getDeclaredFields());
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        for (Field field : fields) {
            TaskParameter taskParameter = field.getAnnotation(TaskParameter.class);
            if (taskParameter != null) {
                Class taskParameterType = field.getType();
                String taskParameterName = taskParameter.value();
                Object taskParameterValue = getTaskParameterValue(jobDataMap, taskParameterType, taskParameterName);

                System.out.println(field.getName() + " = " + taskParameterValue);
                try {
                    field.set(this, taskParameterValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        this.run();
    }

    // TODO: Refine mechanism, can match several fields at once
    private Object getTaskParameterValue(JobDataMap jobDataMap, Class taskParameterType, String taskParameterName) {
        Object taskParameterValue = null;
        if (!taskParameterName.isEmpty() && jobDataMap.containsKey(taskParameterName)
                && jobDataMap.get(taskParameterName).getClass().isAssignableFrom(taskParameterType)) {
            taskParameterValue = jobDataMap.get(taskParameterName);
        } else if (jobDataMap.containsKey(taskParameterType.getSimpleName())
                && jobDataMap.get(taskParameterType.getSimpleName()).getClass().isAssignableFrom(taskParameterType)) {
            taskParameterValue = jobDataMap.get(taskParameterType.getSimpleName());
        }
        return taskParameterValue;
    }

    public abstract void run();



}
