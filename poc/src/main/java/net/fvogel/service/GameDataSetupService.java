package net.fvogel.service;

import java.util.Arrays;
import java.util.List;

import net.fvogel.model.AccountRegistration;
import net.fvogel.model.Planet;
import net.fvogel.model.SolarSystem;
import net.fvogel.model.typing.AtmosphereType;
import net.fvogel.model.typing.PlanetSurfaceType;
import net.fvogel.model.typing.PlanetType;
import net.fvogel.model.typing.StarType;
import net.fvogel.repo.PlanetRepository;
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
    AccountService accountService;

    private List<String> systemNames = Arrays.asList(
            "Nermur",
            "Kyost",
            "Dinsaar",
            "Nhas Kw'heelst",
            "Shinsan"
    );

    public void registerAccount() {
        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setEmail("voll@depp.com");
        accountRegistration.setPassword("1234");
        accountRegistration.setPasswordConfirmation("1234");
        accountRegistration.setUserName("J.Depp");

        accountService.createAccountFromRegistration(accountRegistration);
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
            planet.setHydrogenResources((short)999);
            planet.setAtmosphere(AtmosphereType.GAS);
            planet.setSurface(PlanetSurfaceType.GAS);
            planet.setTempUpperBound((short) (temperatureBase * 1.1));
            planet.setTempLowerBound((short) (temperatureBase * 0.9));
        } else {
            planet.setDiameter((short)randomizer.getRandomInt(120, 19000));
            planet.setHydrogenResources((short)randomizer.getRandomInt(24));
            planet.setIronResources((short)randomizer.getRandomInt(24));
            planet.setSiliconResources((short)randomizer.getRandomInt(12));
            planet.setTempUpperBound((short) (temperatureBase * 1.3));
            planet.setTempLowerBound((short) (temperatureBase * 0.7));
        }

        planetRepository.save(planet);
    }

}
