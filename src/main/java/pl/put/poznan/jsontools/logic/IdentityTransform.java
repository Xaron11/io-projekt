package pl.put.poznan.jsontools.logic;

/**
 * Transform that returns the document unchanged; the start of every chain.
 */
public class IdentityTransform implements JsonTransform {
    /**
     * @param document input document
     * @return the same document
     */
    @Override
    public JsonDocument apply(JsonDocument document) {
        return document;
    }
}
