package net.fvogel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TextureResourceService {

    public static final String TEXTURE_TOPOLOGY = "topology";

    private static Map<String, List> textureMap = loadTextureMap();

    public List<String> getAllTopologyTextures() {
        return textureMap.get(TEXTURE_TOPOLOGY);
    }

    private static Map<String, List> loadTextureMap() {
        Map<String, List> textureMap = new ConcurrentHashMap<String, List>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            List<Resource> resources = Arrays.asList(resolver.getResources("**/textures/*/*.png"));
            List<String> topologies = new ArrayList<String>();
            for (Resource resource : resources) {
                if (resource.getFile().getAbsolutePath().contains("/" + TEXTURE_TOPOLOGY + "/")) {
                    topologies.add("/textures/" + TEXTURE_TOPOLOGY + "/" + resource.getFilename());
                }
            }
            textureMap.put(TEXTURE_TOPOLOGY, topologies);
        } catch (IOException e) {
            log.error("Error while loading textures", e);
        }
        return textureMap;
    }
}
