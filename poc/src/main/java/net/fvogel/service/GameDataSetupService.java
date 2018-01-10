package net.fvogel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fvogel.model.Account;
import net.fvogel.model.AccountRegistration;
import net.fvogel.model.Nation;
import net.fvogel.model.Planet;
import net.fvogel.model.Resource;
import net.fvogel.model.SolarSystem;
import net.fvogel.model.typing.AtmosphereType;
import net.fvogel.model.typing.PlanetSurfaceType;
import net.fvogel.model.typing.PlanetType;
import net.fvogel.model.typing.ResourceType;
import net.fvogel.model.typing.StarType;
import net.fvogel.repo.PlanetRepository;
import net.fvogel.repo.ResourceRepository;
import net.fvogel.repo.SolarSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameDataSetupService {

    @Autowired
    private RandomizerService randomizer;

    @Autowired
    private SolarSystemRepository solarSystemRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    NationService nationService;

    private List<String> systemNames = Arrays.asList(
            "Nermur",
            "Kyost",
            "Dinsaar",
            "Nhas Kw'heelst",
            "Shinsan"
    );

    public void registerAccountWithNation() {
        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setEmail("voll@depp.com");
        accountRegistration.setPassword("1234");
        accountRegistration.setPasswordConfirmation("1234");
        accountRegistration.setUserName("J.Depp");

        Account account = accountService.createAccountFromRegistration(accountRegistration);

        Nation nation = new Nation();
        nation.setName("U.S.P.");
        nationService.registerNewNation(nation, account);
    }

    public void createPlanets() {
        for (String systemName : systemNames) {
            SolarSystem solarSystem = new SolarSystem();
            solarSystem.setName(systemName);
            solarSystem.setType(randomizer.getRandomEnumConstant(StarType.class));
            solarSystem.setCoordX(randomizer.getRandomInt(1000));
            solarSystem.setCoordY(randomizer.getRandomInt(1000));
            solarSystem.setCoordZ(randomizer.getRandomInt(1000));
            solarSystemRepository.save(solarSystem);
            createPlanetsForSolarSystem(solarSystem);
        }

    }

    private void createPlanetsForSolarSystem(SolarSystem solarSystem) {
        for (int i = 0; i <= randomizer.getRandomInt(12); i++) {
            createPlanetForSolarSystem(solarSystem, i);
        }
    }

    private void createPlanetForSolarSystem(SolarSystem solarSystem, int i) {
        Planet planet = new Planet();
        planet.setName(solarSystem.getName() + " " + (i + 1));
        planet.setSolarSystem(solarSystem);

        short distance = (short)randomizer.getRandomInt(10, 10000);
        planet.setDistance(distance);

        PlanetType type = PlanetType.values()[randomizer.getRandomInt(2)];
        planet.setType(type);

        double temperatureBase = 0;
        String typeString = solarSystem.getType().toString();
        if (typeString.contains("DWARF")) {
            temperatureBase = 1;
        } else if (typeString.contains("GIANT") || typeString.contains("TWIN") || typeString.contains("TRIPLET")) {
            temperatureBase = 2;
        }
        temperatureBase = (temperatureBase / (distance)) * 500000l;
        System.out.println(distance + " - " + temperatureBase);

        if (PlanetType.GAS.equals(type)) {
            planet.setDiameter((short)randomizer.getRandomInt(15000, 25000));
            planet.setAtmosphere(AtmosphereType.GAS);
            planet.setSurface(PlanetSurfaceType.GAS);
            planet.setTempUpperBound((short) (temperatureBase * 1.1));
            planet.setTempLowerBound((short) (temperatureBase * 0.9));
        } else {
            planet.setDiameter((short)randomizer.getRandomInt(120, 19000));
            planet.setTempUpperBound((short) (temperatureBase * 1.3));
            planet.setTempLowerBound((short) (temperatureBase * 0.7));
            planet.setAtmosphere(randomizer.getRandomEnumConstant(AtmosphereType.class));
            planet.setSurface(randomizer.getRandomEnumConstant(PlanetSurfaceType.class));
        }

        planetRepository.save(planet);
        planet.setResources(generateResources(planet));
        planetRepository.save(planet);
    }

    private List<Resource> generateResources(Planet planet) {

        List<Resource> resourceList = new ArrayList<>();

        for (ResourceType resourceType : ResourceType.values()) {
            Resource resource = new Resource(resourceType);
            resource.setMineCapacity(randomizer.getRandomInt(24));
            resource.setMineCount(0);
            resource.setPlanet(planet);
            resource.setStock(0D);
            resourceList.add(resource);

            if (PlanetType.GAS.equals(planet.getType())) {
                if (ResourceType.H2.equals(resourceType)) {
                    resource.setMineCapacity(randomizer.getRandomInt(999));
                } else {
                    resource.setMineCapacity(randomizer.getRandomInt(0));
                }
            }

        }

        resourceRepository.save(resourceList);

        return resourceList;

    }

}
