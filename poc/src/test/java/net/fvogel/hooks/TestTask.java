package net.fvogel.hooks;

import net.fvogel.repo.PlanetRepository;
import net.fvogel.scheduling.job.Task;
import net.fvogel.scheduling.job.TaskParameter;
import org.springframework.beans.factory.annotation.Autowired;

public class TestTask extends Task {

    @TaskParameter("testLong")
    public Long testLong;

    @TaskParameter
    public Integer testInt;

    public Long anotherId;


    @Override
    public void run() {

    }

}
