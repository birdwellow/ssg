package net.fvogel.web;

import net.fvogel.model.Nation;
import net.fvogel.repo.NationRepository;
import net.fvogel.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nations")
public class NationsResource {

    @Autowired
    NationRepository nationRepository;

    @Autowired
    NationService nationService;

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public Nation getNation(@PathVariable() String uuid) {
        return nationRepository.findOneByUuid(uuid);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createNation(@RequestBody Nation nationData) {
        Nation nation = nationService.registerNewNation(nationData);
        return nation.getUuid();
    }

}
