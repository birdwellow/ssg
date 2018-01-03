package net.fvogel.hooks;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import net.fvogel.model.Planet;
import net.fvogel.model.SolarSystem;
import net.fvogel.model.meta.MetaInitStatus;
import net.fvogel.model.typing.AtmosphereType;
import net.fvogel.model.typing.PlanetSurfaceType;
import net.fvogel.model.typing.PlanetType;
import net.fvogel.model.typing.StarType;
import net.fvogel.repo.MetaInitStatusRepository;
import net.fvogel.repo.PlanetRepository;
import net.fvogel.repo.SolarSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataMigrationHook {

    List<String> systemNames = Arrays.asList(
        "Nermur",
        "Kyost",
        "Dinsaar",
        "Nhas Kw'heelst",
        "Shinsan"
    );

    @Autowired
    MetaInitStatusRepository metaInitStatusRepository;

    @Autowired
    SolarSystemRepository solarSystemRepository;

    @Autowired
    PlanetRepository planetRepository;

    @PostConstruct
    public void migrate() {
        List<MetaInitStatus> states = metaInitStatusRepository.findAll();
        if (states != null && states.size() > 0) {
            return;
        }

        MetaInitStatus status = new MetaInitStatus();

        for (String systemName : systemNames) {
            SolarSystem solarSystem = new SolarSystem();
            solarSystem.setName(systemName);
            solarSystem.setType(getRandomStarType());
            solarSystem.setCoordX(getRandomInt(1000));
            solarSystem.setCoordY(getRandomInt(1000));
            solarSystem.setCoordZ(getRandomInt(1000));
            solarSystemRepository.save(solarSystem);
            createPlanets(solarSystem);
        }

        metaInitStatusRepository.save(status);

    }

    private void createPlanets(SolarSystem solarSystem) {
        for (int i = 0; i <= getRandomInt(12); i++) {
            Planet planet = new Planet();
            planet.setName(solarSystem.getName() + " " + (i + 1));
            planet.setSolarSystem(solarSystem);

            short distance = (short)getRandomInt(10, 10000);
            planet.setDistance(distance);

            PlanetType type = PlanetType.values()[getRandomInt(2)];
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
                planet.setDiameter((short)getRandomInt(15000, 25000));
                planet.setHydrogenResources((short)999);
                planet.setAtmosphere(AtmosphereType.GAS);
                planet.setSurface(PlanetSurfaceType.GAS);
                planet.setTempUpperBound((short) (temperatureBase * 1.1));
                planet.setTempLowerBound((short) (temperatureBase * 0.9));
            } else {
                planet.setDiameter((short)getRandomInt(120, 19000));
                planet.setHydrogenResources((short)getRandomInt(24));
                planet.setIronResources((short)getRandomInt(24));
                planet.setSiliconResources((short)getRandomInt(12));
                planet.setTempUpperBound((short) (temperatureBase * 1.3));
                planet.setTempLowerBound((short) (temperatureBase * 0.7));
            }

            planetRepository.save(planet);
        }
    }

    private StarType getRandomStarType() {
        int range = StarType.values().length;
        int randomIndex = getRandomInt(range);
        return StarType.values()[randomIndex];
    }

    int getRandomInt() {
        return (int) Math.random() * 1000;
    }

    int getRandomInt(int range) {
        return (int) (Math.random() * range);
    }

    int getRandomInt(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
    }
}
