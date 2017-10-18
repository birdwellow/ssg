package net.fvogel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("definitions")
public class DefinitionController {

    private Map<String, String> cacheRepo = new HashMap<String, String>();

    public DefinitionController() {
        cacheRepo.put("testkey", "testvalue");
    }

    @RequestMapping(method = GET)
    public Map<String, String> getDefinitions() {
        return cacheRepo;
    }

    @RequestMapping(path = "{name}", method = GET)
    public String getDefinition(@PathVariable String name) {
        return cacheRepo.get(name);
    }

    @RequestMapping(method = POST)
    public void saveDefinition(@RequestBody Definition definition) {
        cacheRepo.put(definition.getName(), definition.getJson());
    }

}
