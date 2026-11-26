package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.Test;
import pl.put.poznan.jsontools.logic.exception.InvalidTransformOptionsException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TransformChainBuilderTest {

    private final List<String> log = new ArrayList<>();
    private final JsonDocument document = new JsonDocument(JsonNodeFactory.instance.objectNode(), OutputFormat.COMPACT);

    private TransformChainBuilder builderWith(String... names) {
        final LinkedHashMap<String, TransformFactory> registry = new LinkedHashMap<>();
        for (String name : names) {
            registry.put(name, (inner, options) -> new RecordingDecorator(inner, name, log));
        }
        return new TransformChainBuilder(registry);
    }

    @Test
    void noOptionsReturnsDocumentUnchanged() {
        TransformChainBuilder testCase = builderWith("a", "b", "c");
        TransformOptions options = TransformOptions.fromNames(new String[] {});

        JsonDocument result = testCase.build(options).apply(document);

        assertThat(result).isSameAs(document);
        assertThat(log).isEmpty();
    }

    @Test
    void appliesTransformsInRegistryOrder() {
        TransformChainBuilder testCase = builderWith("a", "b", "c");
        TransformOptions options = TransformOptions.fromNames(new String[] {"c", "a"});

        testCase.build(options).apply(document);

        assertThat(log).containsExactly("a", "c");
    }

    @Test
    void unknownOptionsAreReportedInAlphabeticalOrder() {
        TransformChainBuilder testCase = builderWith("a");
        TransformOptions options = TransformOptions.fromNames(new String[] {"xyz", "abc"});

        assertThatThrownBy(() -> testCase.build(options))
                .isInstanceOf(InvalidTransformOptionsException.class)
                .hasMessage("Unknown transforms: abc, xyz");
    }

    @Test
    void minifyAndPrettyCannotBeUsedTogether() {
        TransformChainBuilder testCase = builderWith(TransformChainBuilder.MINIFY, TransformChainBuilder.PRETTY);
        TransformOptions options = TransformOptions.fromNames(
            new String[]{TransformChainBuilder.MINIFY, TransformChainBuilder.PRETTY});

        assertThatThrownBy(() -> testCase.build(options))
            .isInstanceOf(InvalidTransformOptionsException.class)
            .hasMessage("Transforms minify and pretty cannot be used together");
    }

    @Test
    void registryIsCopied() {
        Map<String, TransformFactory> registry = new LinkedHashMap<>();
        registry.put("a", (inner, options) -> new RecordingDecorator(inner, "a", log));
        TransformChainBuilder testCase = new TransformChainBuilder(registry);
        registry.put("b", (inner, options) -> new RecordingDecorator(inner, "b", log));
        TransformOptions options = TransformOptions.fromNames(new String[] {"b"});

        assertThatThrownBy(() -> testCase.build(options))
            .isInstanceOf(InvalidTransformOptionsException.class)
            .hasMessage("Unknown transforms: b");
    }

    static class RecordingDecorator extends JsonTransformDecorator {
        private final String name;
        private final List<String> log;

        RecordingDecorator(JsonTransform inner, String name, List<String> log) {
            super(inner);
            this.name = name;
            this.log = log;
        }

        @Override
        protected JsonDocument decorate(JsonDocument document) {
            log.add(name);
            return document;
        }
    }
}
