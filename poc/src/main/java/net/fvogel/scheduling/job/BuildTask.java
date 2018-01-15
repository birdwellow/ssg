package net.fvogel.scheduling.job;

import net.fvogel.repo.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BuildTask extends Task {

    @Autowired
    PlanetRepository planetRepository;

    @TaskParameter("planetId")
    Long planetId;

    @TaskParameter
    Integer someInt;

    Long anotherId;

    @Override
    public void run() {
        System.out.println(String.format("execute with dependency: %s and planetId: %d, someInt: %d",
                planetRepository,
                planetId,
                someInt));

    }

}
