package net.fvogel.hooks;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import net.fvogel.model.Planet;
import net.fvogel.model.SolarSystem;
import net.fvogel.model.meta.MetaInitStatus;
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
            planet.setDiameter((short)getRandomInt(120, 24560));
            planet.setDistance((short)getRandomInt(10, 10000));
            planet.setHydrogenCapacity((short)getRandomInt(24));
            planet.setIronCapacity((short)getRandomInt(24));
            planet.setSiliconCapacity((short)getRandomInt(12));
            planet.setName(solarSystem.getName() + " " + (i + 1));
            planet.setType(PlanetType.values()[getRandomInt(2)]);

            planet.setSolarSystem(solarSystem);
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
