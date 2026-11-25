package pl.put.poznan.jsontools.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.jsontools.logic.JsonTransformer;

/**
 * REST entry point of JSON Tools, available under {@code /api/json}.
 *
 * <p>Accepts a JSON document, parses it into a Jackson {@link JsonNode} tree and passes it
 * to {@link JsonTransformer} together with the requested transforms.
 */
@RestController
@RequestMapping("/api/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Transforms the JSON document sent in the request body.
     *
     * <p>Example: {@code POST /api/json?transforms=minify,sort} with a JSON body.
     *
     * @param json       request body; must be a valid JSON document
     * @param transforms names of the transforms to apply, in order, given as a
     *                   comma-separated {@code transforms} query parameter (optional)
     * @return the transformed document as JSON
     * @throws JsonProcessingException if the request body is not valid JSON
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public String transform(@RequestBody String json,
                            @RequestParam(value = "transforms", defaultValue = "") String[] transforms) throws JsonProcessingException {

        JsonNode rootJsonNode = mapper.readTree(json);

        logger.debug(rootJsonNode.toString());

        JsonTransformer transformer = new JsonTransformer(transforms);
        return transformer.transform(rootJsonNode);
    }
}
