package pl.put.poznan.jsontools.logic;

import java.util.Objects;

/**
 * Base decorator: runs the wrapped transform first, then {@link #decorate(JsonDocument)}.
 */
public abstract class JsonTransformDecorator implements JsonTransform {
    private final JsonTransform inner;

    /**
     * @param inner wrapped transform
     */
    protected JsonTransformDecorator(JsonTransform inner) {
        this.inner = Objects.requireNonNull(inner, "Transform must not be null");
    }

    /**
     * @param document input document
     * @return document after the wrapped transform and this decorator
     */
    @Override
    public final JsonDocument apply(JsonDocument document) {
        JsonDocument transformed = inner.apply(document);
        return decorate(transformed);
    }

    /**
     * Applies this decorator's own change.
     *
     * @param document result of the wrapped transform
     * @return changed document
     */
    protected abstract JsonDocument decorate(JsonDocument document);
}
