package net.fvogel.web;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.fvogel.exception.NoEntityExistingException;
import net.fvogel.model.Account;
import net.fvogel.model.Nation;
import net.fvogel.model.Planet;
import net.fvogel.model.Resource;
import net.fvogel.model.typing.ResourceType;
import net.fvogel.repo.NationRepository;
import net.fvogel.repo.PlanetRepository;
import net.fvogel.repo.ResourceRepository;
import net.fvogel.scheduling.job.BuildJob;
import net.fvogel.scheduling.job.ResourcesUpdateJob;
import net.fvogel.service.AccountService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
@RequestMapping("/planets")
public class PlanetsResource {

    @Autowired
    Scheduler scheduler;

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Planet> getCurrentUsersPlanets(HttpServletResponse response) {
        Account account = accountService.getCurrentAccount();
        if (account == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return account.getNation().getPlanets();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Planet getPlanetById(@PathVariable("id") Long id) {
        Planet planet = planetRepository.findOne(id);
        if (planet == null) {
            throw new NoEntityExistingException("No planet with ID '" + id + "' exists");
        }
        return planet;
    }

    @RequestMapping(value = "/{id}/build", method = RequestMethod.POST)
    public void buildMines(
            @PathVariable("id") Long planetId,
            @RequestParam("type") String type,
            @RequestParam("amount") Integer amount
    ) throws SchedulerException {

        Account currentAccount = accountService.getCurrentAccount();
        Planet planet = getPlanetById(planetId);
        Account planetOwningAccount = planet.getNation().getAccount();

        ResourceType resourceType = ResourceType.valueOf(type.toUpperCase());

        if (currentAccount.getId() != planetOwningAccount.getId()) {
            throw new AccessDeniedException("Planet does not belong to current user");
        }
        if (planet == null) {
            throw new NoEntityExistingException("No planet with ID '" + planetId + "' exists");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot build less than zero mines");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("'" + type + "' is not a valid resource type");
        }

        int price = 15 * amount;
        Nation nation = currentAccount.getNation();
        Resource resource = planet.getResource(resourceType);
        if (nation.getCredits() - price < 0) {
            throw new IllegalArgumentException("Nation does not have enough credits");
        }
        if (resource.getMineCapacity() < resource.getMineCount() + amount) {
            throw new IllegalArgumentException("Mine capacity on this planet exceeded");
        }

        // TODO: Instead of simply building, add energy supply and issue timer

        nation.setCredits(nation.getCredits() - price);
        resource.setMineCount(resource.getMineCount() + amount);

        nationRepository.save(nation);
        resourceRepository.save(resource);


        JobDetail job = newJob(BuildJob.class)
                .withIdentity(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .build();
        // TODO: Use Spring to inject dependencies (cannot be persisted)
        // The rest (usually primitives/Serializables) are to be handed over by the jobDataMap
        job.getJobDataMap().put("repo", planetRepository);
        job.getJobDataMap().put("planetId", planetId);

        Trigger trigger = newTrigger()
                .withIdentity(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .startAt(new Date(new Date().getTime() + 5000))
                .build();

        scheduler.scheduleJob(job, trigger);

    }

}
