package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JsonDocumentSerializerTest {
    private final JsonDocumentSerializer documentSerializer = new JsonDocumentSerializer();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void compactProducesJsonWithoutWhitespace() throws JsonProcessingException {
        JsonNode rootJsonNode = objectMapper.readTree("{ \"a\" : 1, \"b\" : [1, 2] }");
        JsonDocument document = new JsonDocument(rootJsonNode, OutputFormat.COMPACT);

        String testCase = documentSerializer.serialize(document);
        assertThat(testCase).isEqualTo("{\"a\":1,\"b\":[1,2]}");
    }

    @Test
    void prettyProducesMultilineJson() throws JsonProcessingException {
        JsonNode rootJsonNode = objectMapper.readTree("{ \"a\" : 1, \"b\" : [1, 2] }");
        JsonDocument document = new JsonDocument(rootJsonNode, OutputFormat.PRETTY);

        String testCase = documentSerializer.serialize(document);
        assertThat(testCase).contains("\n");
    }

    @Test
    void prettyKeepsTheSameContent() throws JsonProcessingException {
        JsonNode rootJsonNode = objectMapper.readTree("{ \"a\" : 1, \"b\" : [1, 2] }");
        JsonDocument document = new JsonDocument(rootJsonNode, OutputFormat.PRETTY);

        String testCase = documentSerializer.serialize(document);
        JsonNode testJsonNode = objectMapper.readTree(testCase);
        assertThat(testJsonNode).isEqualTo(rootJsonNode);
    }
}
