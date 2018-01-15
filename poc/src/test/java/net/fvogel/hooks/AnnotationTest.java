package net.fvogel.hooks;

import java.util.HashMap;
import java.util.Map;

import net.fvogel.scheduling.job.BuildTask;
import net.fvogel.scheduling.job.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationTest {

    @Mock
    JobExecutionContext context;

    @Mock
    JobDetail jobDetail;

    @Before
    public void setUp() {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("planetId", 3L);
        jobDataMap.put("Long", 3L);
        jobDataMap.put("Integer", 15);

        when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);
        when(context.getJobDetail()).thenReturn(jobDetail);
    }

    @Test
    public void testJobAnnotations() throws JobExecutionException {
        TestTask testTask = new TestTask();
        testTask.execute(context);

        Assert.assertEquals(3L, (long) testTask.testLong);
        Assert.assertEquals(15, (long) testTask.testInt);
    }

}
