package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

/**
 * Immutable JSON tree with its output format, passed through the chain of transforms.
 */
public class JsonDocument {
    private final JsonNode node;
    private final OutputFormat format;

    /**
     * @param node   root of the JSON tree
     * @param format output format
     */
    public JsonDocument(JsonNode node, OutputFormat format) {
        this.node = Objects.requireNonNull(node, "Node must not be null");
        this.format = Objects.requireNonNull(format, "Format must not be null");
    }

    /**
     * Returns the JSON tree. It is mutable, so transforms should change a {@code deepCopy()}.
     *
     * @return root of the JSON tree
     */
    public JsonNode getNode() {
        return node;
    }

    /**
     * @return output format
     */
    public OutputFormat getFormat() {
        return format;
    }

    /**
     * @param newNode new JSON tree
     * @return new document with the given tree and the same format
     */
    public JsonDocument withNode(JsonNode newNode) {
        return new JsonDocument(newNode, format);
    }

    /**
     * @param newFormat new output format
     * @return new document with the same tree and the given format
     */
    public JsonDocument withFormat(OutputFormat newFormat) {
        return new JsonDocument(node, newFormat);
    }
}
