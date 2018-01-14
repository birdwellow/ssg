package net.fvogel.scheduling.job;

import net.fvogel.repo.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BuildTask extends Task {

    @Autowired
    PlanetRepository planetRepository;

    @Override
    public void execute(Object... parameters) {

        System.out.println("execute with dependency: " + planetRepository);

    }

}
