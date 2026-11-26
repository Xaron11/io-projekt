package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serializes a {@link JsonDocument} according to its {@link OutputFormat}.
 */
public class JsonDocumentSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Creates a serializer.
     */
    public JsonDocumentSerializer() {}

    /**
     * @param document document to serialize
     * @return JSON string
     * @throws IllegalStateException if serialization fails (server-side error)
     */
    public String serialize(JsonDocument document) {
        final OutputFormat format = document.getFormat();

        try {
            switch (format) {
                case COMPACT:
                    return objectMapper.writeValueAsString(document.getNode());
                case PRETTY:
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(document.getNode());
                default:
                    throw new IllegalStateException("Unsupported output format: " + format);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize JSON document", e);
        }
    }
}
