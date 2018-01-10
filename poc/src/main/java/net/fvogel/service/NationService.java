package net.fvogel.service;

import java.util.List;
import java.util.stream.Collectors;

import net.fvogel.exception.ConflictingEntitiesException;
import net.fvogel.model.Account;
import net.fvogel.model.Nation;
import net.fvogel.model.Planet;
import net.fvogel.model.typing.PlanetType;
import net.fvogel.repo.NationRepository;
import net.fvogel.repo.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NationService {

    @Autowired
    RandomizerService randomizerService;

    @Autowired
    AccountService accountService;

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    NationRepository nationRepository;

    public Nation getNation(Long id) {
        return nationRepository.findOne(id);
    }

    public Nation registerNewNation(Nation nationData) {
        Account account = accountService.getCurrentAccount();

        if (account.getNation() != null) {
            throw new ConflictingEntitiesException("A nation is already associated with that account");
        }

        if (!isNationDataUnique(nationData)) {
            throw new IllegalArgumentException(String.format(
                    "Nation is not unique; either name '%s' is not unique",
                    nationData.getName()
            ));
        }

        Planet unownedPlanet = findAssignablePlanet();
        if (unownedPlanet == null) {
            throw new IllegalStateException("There is no planet left that could be assigned to this new nation");
        }

        Nation nation = new Nation();
        nation.setCredits(200);
        nation.setName(nationData.getName());
        nation.setAccount(account);
        nation.getPlanets().add(unownedPlanet);

        nationRepository.save(nation);

        return nation;
    }

    private Planet findAssignablePlanet() {
        List<Planet> allPlanets = planetRepository.findAll();
        List<Planet> planetsWithoutOwner = allPlanets.stream()
                .filter((planet -> (
                        planet.getNation() == null
                        && PlanetType.SOLID.equals(planet.getType())
                )))
                .collect(Collectors.toList());
        int randomIndex = randomizerService.getRandomInt(planetsWithoutOwner.size());
        return planetsWithoutOwner.get(randomIndex);
    }

    private boolean isNationDataUnique(Nation nationData) {
        Nation existingNation = nationRepository.findOneByName(nationData.getName());
        return existingNation == null;
    }

}
