package net.fvogel.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.fvogel.exception.NoEntityExistingException;
import net.fvogel.model.Account;
import net.fvogel.model.Planet;
import net.fvogel.model.typing.ResourceType;
import net.fvogel.repo.NationRepository;
import net.fvogel.repo.PlanetRepository;
import net.fvogel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planets")
public class PlanetsResource {

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    NationRepository nationRepository;

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
    ) {
        Account account = accountService.getCurrentAccount();
        Planet planet = getPlanetById(planetId);
        if (planet == null) {
            throw new NoEntityExistingException("No planet with ID '" + planetId + "' exists");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot build less than zero mines");
        }
        ResourceType resourceType = ResourceType.valueOf(type.toUpperCase());
//        switch (resourceType) {
//            case ResourceType.FE:
//                Integer credits = account.getNation().getCredits();
//                Integer price = amount * 35;
//                if (credits - price < 0) {
//                    throw new IllegalArgumentException("You don't have enough credits!");
//                }
//                account.getNation().setCredits(credits - price);
//                planet.setIronMines((short)(planet.getIronMines() + amount.shortValue()));
//                nationRepository.save(account.getNation());
//                planetRepository.save(planet);
//                break;
//            case "h2": break;
//            case "si": break;
//            default: break;
//        }

    }

}
