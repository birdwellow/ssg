package net.fvogel.scheduling.job;

import java.util.List;

import net.fvogel.config.SchedulingConfig;
import net.fvogel.model.Resource;
import net.fvogel.repo.ResourceRepository;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ResourcesUpdateJob implements Job {

    @Autowired
    ResourceRepository resourceRepository;

    double resourceIncrementPerMillisecond = 0.0000005;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Resource> resources = resourceRepository.findAll();
        System.out.println("updating " + resources.size() + " resources");

        for (Resource resource : resources) {
            Long now = System.currentTimeMillis();
            Long diff = now - resource.getLastUpdatedAt();
            double stock = resource.getStock();
            int mines = resource.getMineCount();
            stock += mines * diff * resourceIncrementPerMillisecond;
            resource.setStock(stock);
            resource.setLastUpdatedAt(now);
        }

        resourceRepository.save(resources);
    }

    @Bean(name = "jobWithSimpleTriggerBean")
    public JobDetailFactoryBean jobDetailFactoryBean() {
        return SchedulingConfig.createJobDetail(this.getClass());
    }

    @Bean(name = "jobWithSimpleTriggerBeanTrigger")
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(@Qualifier("jobWithSimpleTriggerBean") JobDetail jobDetail) {
        return SchedulingConfig.createTrigger(jobDetail, 5000);
    }

}
