package net.fvogel.scheduling.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BuildJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Built on planet " + context.getJobDetail().getJobDataMap().get("planetId") + " with "
                + context.getJobDetail().getJobDataMap().get("repo"));

    }

}
