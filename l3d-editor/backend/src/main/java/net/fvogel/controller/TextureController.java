package net.fvogel.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import net.fvogel.TextureResourceService;
import net.fvogel.model.Definition;
import net.fvogel.persistence.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("textures")
public class TextureController {

    @Autowired
    TextureResourceService textureResourceService;

    @RequestMapping(value = "topology", method = GET)
    public List<String> getAllTopologyTextures() throws IOException {
        return textureResourceService.getAllTopologyTextures();
    }

}
