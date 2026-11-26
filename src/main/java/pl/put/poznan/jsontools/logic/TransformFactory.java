package pl.put.poznan.jsontools.logic;

/**
 * Creates a decorator wrapping the chain built so far.
 */
@FunctionalInterface
public interface TransformFactory {
    /**
     * @param inner   chain built so far
     * @param options request options
     * @return {@code inner} wrapped in a new decorator
     */
    JsonTransform create(JsonTransform inner, TransformOptions options);
}
