package net.fvogel.scheduling.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class Task implements Job {

    public final void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO: Allow Task implementations to annotate one method for execution and
        //       inject corresponding parameters (maybe also annotated with a name)
        this.execute();
    }

    public abstract void execute(Object... parameters);



}
