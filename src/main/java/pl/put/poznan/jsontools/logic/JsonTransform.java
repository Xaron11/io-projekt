package pl.put.poznan.jsontools.logic;

/**
 * JSON transform (Decorator pattern). Implementations must not modify the input document
 * and must never return {@code null}.
 */
@FunctionalInterface
public interface JsonTransform {
    /**
     * @param document input document
     * @return transformed document
     */
    JsonDocument apply(JsonDocument document);
}
