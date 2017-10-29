package net.fvogel.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import net.fvogel.model.Definition;
import net.fvogel.persistence.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("images")
public class ImageController {

    @RequestMapping(method = POST)
    public void saveImages(MultipartHttpServletRequest request) {
        for (Iterator<String> it = request.getFileNames(); it.hasNext(); ) {
            String fileName = it.next();
            MultipartFile file = request.getFile(fileName);
        }
    }

}
