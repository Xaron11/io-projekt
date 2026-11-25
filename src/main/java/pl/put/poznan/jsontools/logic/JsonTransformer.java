package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Applies a sequence of named transforms to a JSON document.
 *
 * <p>The document is passed in as a Jackson {@link JsonNode} tree (the object model of the
 * input), so individual transforms can operate on the structure instead of on raw text.
 */
public class JsonTransformer {

    private final String[] transforms;

    /**
     * Creates a transformer for the given transforms.
     *
     * @param transforms names of the transforms to apply, in order (e.g. {@code "minify"});
     *                   an empty array means the document is returned unchanged
     */
    public JsonTransformer(String[] transforms) {
        this.transforms = transforms;
    }

    /**
     * Applies the configured transforms to the given JSON tree.
     *
     * <p>Transforms are not implemented yet, so the document is currently returned
     * unchanged, serialized as compact JSON.
     *
     * @param rootJsonNode root node of the parsed input JSON
     * @return the transformed document serialized as a JSON string
     */
    public String transform(JsonNode rootJsonNode) {
        // TODO: apply the requested transforms
        return rootJsonNode.toString();
    }
}
