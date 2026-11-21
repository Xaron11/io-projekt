package pl.put.poznan.jsontools.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.jsontools.logic.JsonTransformer;

import java.util.Arrays;


@RestController
@RequestMapping("/api/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @PostMapping(consumes = "application/json", produces = "application/json")
    public String transform(@RequestBody String json,
                            @RequestParam(value = "transforms", defaultValue = "") String[] transforms) {

        logger.debug(json);
        logger.debug(Arrays.toString(transforms));

        JsonTransformer transformer = new JsonTransformer(transforms);
        return transformer.transform(json);
    }
}
