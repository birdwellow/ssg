package net.fvogel.scheduling.job;

import net.fvogel.repo.PlanetRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class BuildJob implements Job {

    @Autowired
    PlanetRepository planetRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Built on planet " + context.getJobDetail().getJobDataMap().get("planetId") + " with "
                + planetRepository);

    }

}
