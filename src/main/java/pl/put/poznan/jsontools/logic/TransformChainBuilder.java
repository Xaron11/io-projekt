package pl.put.poznan.jsontools.logic;

import pl.put.poznan.jsontools.logic.exception.InvalidTransformOptionsException;

import java.util.*;

/**
 * Builds a chain of decorators from request options, in the order of the registry.
 */
public class TransformChainBuilder {
    /** Name of the minify transform. */
    public static final String MINIFY = "minify";
    /** Name of the pretty transform. */
    public static final String PRETTY = "pretty";

    private final Map<String, TransformFactory> registry;

    /**
     * @param registry transform names mapped to decorator factories (copied, order kept)
     */
    public TransformChainBuilder(Map<String, TransformFactory> registry) {
        this.registry = Collections.unmodifiableMap(new LinkedHashMap<>(registry));
    }

    /**
     * @param options request options
     * @return chain of enabled transforms
     * @throws InvalidTransformOptionsException if a transform is unknown or minify and pretty are both enabled
     */
    public JsonTransform build(TransformOptions options) {
        final Set<String> unknownOptions = new TreeSet<>();
        JsonTransform chain = new IdentityTransform();

        for (String option : options.getTransforms()) {
            if (!registry.containsKey(option)) {
                unknownOptions.add(option);
            }
        }
        if (!unknownOptions.isEmpty()) {
            throw new InvalidTransformOptionsException("Unknown transforms: " + String.join(", ", unknownOptions));
        }
        if (options.isEnabled(MINIFY) && options.isEnabled(PRETTY)) {
            throw new InvalidTransformOptionsException("Transforms minify and pretty cannot be used together");
        }

        for (Map.Entry<String, TransformFactory> entry : registry.entrySet()) {
            if (options.isEnabled(entry.getKey())) {
                chain = entry.getValue().create(chain, options);
            }
        }
        return chain;
    }

    /**
     * Registry of all transforms. Register filters (keep, remove) before minify and pretty.
     *
     * @return builder with the default registry
     */
    public static TransformChainBuilder withDefaultTransforms() {
        return new TransformChainBuilder(new LinkedHashMap<>());
    }
}
