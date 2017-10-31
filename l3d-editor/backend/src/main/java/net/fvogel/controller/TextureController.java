package net.fvogel.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.fvogel.TextureResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("textures")
public class TextureController {

    @Autowired
    TextureResourceService textureResourceService;

    @RequestMapping(method = GET)
    public Map<String, List> getAllTextures() throws IOException {
        return textureResourceService.getAllTextures();
    }

}
