package net.fvogel.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import net.fvogel.model.Definition;
import net.fvogel.persistence.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("definitions")
public class DefinitionController {

    @Autowired
    DefinitionRepository definitionRepository;

    @RequestMapping(method = GET)
    public Map<String, String> getDefinitions() {
        return mapToPlainMap(definitionRepository.findAll());
    }

    @RequestMapping(method = POST)
    public void saveDefinition(@RequestBody Definition definition) {
        if (definition == null) {
            throw new IllegalArgumentException("No definition given");
        }
        try {
            Definition storedDefinition = definitionRepository.findOneByName(definition.getName());
            if (storedDefinition != null) {
                storedDefinition.setJson(definition.getJson());
                definitionRepository.save(storedDefinition);
                return;
            }
            definitionRepository.save(definition);
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException("Definition is not valid");
        }
    }

    @RequestMapping(path = "{name}", method = GET)
    public String getDefinition(@PathVariable String name) {
        Definition result = definitionRepository.findOneByName(name);
        if (result == null) {
            throw new NoSuchElementException(String.format("No definition with name %s found", name));
        }
        return result.getJson();
    }

    private Map mapToPlainMap(Iterable<Definition> definitions) {
        Map<String, String> result = new HashMap<String, String>();
        for (Definition definition : definitions) {
            result.put(definition.getName(), definition.getJson());
        }
        return result;
    }

}
