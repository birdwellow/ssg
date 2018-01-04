package net.fvogel.hooks;

import java.util.List;

import javax.annotation.PostConstruct;

import net.fvogel.model.meta.MetaInitStatus;
import net.fvogel.repo.MetaInitStatusRepository;
import net.fvogel.service.GameDataSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameDataSetupHook {

    @Autowired
    MetaInitStatusRepository metaInitStatusRepository;

    @Autowired
    GameDataSetupService gameDataSetupService;

    @PostConstruct
    public void migrate() {
        List<MetaInitStatus> states = metaInitStatusRepository.findAll();
        if (states != null && states.size() > 0) {
            return;
        }

        gameDataSetupService.createPlanets();

        metaInitStatusRepository.save(new MetaInitStatus());

    }
}
