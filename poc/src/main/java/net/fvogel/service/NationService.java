package net.fvogel.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    PlanetRepository planetRepository;

    @Autowired
    NationRepository nationRepository;

    public Nation registerNewNation(Nation nationData) {

        if (!isNationDataUnique(nationData)) {
            throw new IllegalArgumentException(String.format(
                    "Nation is not unique; either name '%s' or userName '%s' is not unique",
                    nationData.getName(),
                    nationData.getUserName()));
        }

        Planet unownedPlanet = findAssignablePlanet();
        if (unownedPlanet == null) {
            throw new IllegalStateException("There is no planet left that could be assigned to this new nation");
        }

        String uuid = UUID.randomUUID().toString();

        Nation nation = new Nation();
        nation.setUuid(uuid);
        nation.setCredits(200);
        nation.setUserName(nationData.getUserName());
        nation.setName(nationData.getName());

        nationRepository.save(nation);
        unownedPlanet.setNation(nation);
        planetRepository.save(unownedPlanet);

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
        Nation existingNation = nationRepository.findOneByNameOrUserName(nationData.getName(), nationData.getUserName());
        return existingNation == null;
    }

}
