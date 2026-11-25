package pl.put.poznan.jsontools.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.jsontools.logic.TransformOptions;
import pl.put.poznan.jsontools.logic.JsonDocument;
import pl.put.poznan.jsontools.logic.JsonDocumentSerializer;
import pl.put.poznan.jsontools.logic.TransformChainBuilder;
import pl.put.poznan.jsontools.logic.OutputFormat;
import pl.put.poznan.jsontools.logic.exception.InvalidJsonException;

/**
 * REST endpoint {@code /api/json} for transforming JSON documents.
 */
@RestController
@RequestMapping("/api/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);
    private static final ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    private static final TransformChainBuilder chainBuilder = TransformChainBuilder.withDefaultTransforms();
    private static final JsonDocumentSerializer serializer = new JsonDocumentSerializer();

    /**
     * Transforms the JSON document from the request body,
     * e.g. {@code POST /api/json?transforms=minify}.
     *
     * @param json       JSON document
     * @param transforms comma-separated transform names (optional)
     * @return transformed JSON
     * @throws JsonProcessingException if the body is not valid JSON
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public String transform(@RequestBody String json,
                            @RequestParam(value = "transforms", defaultValue = "") String[] transforms) throws JsonProcessingException {

        JsonNode rootJsonNode = mapper.readTree(json);

        if (rootJsonNode.isMissingNode()) {
            throw new InvalidJsonException("Request body is empty");
        }

        logger.debug(rootJsonNode.toString());

        JsonDocument document = new JsonDocument(rootJsonNode, OutputFormat.COMPACT);
        TransformOptions options = TransformOptions.fromNames(transforms);

        return serializer.serialize(chainBuilder.build(options).apply(document));
    }
}
