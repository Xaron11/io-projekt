package pl.put.poznan.jsontools.logic;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Normalized (trimmed, lower-case) names of the transforms requested by the client.
 */
public class TransformOptions {
    private final Set<String> transforms;

    /**
     * @param transforms normalized names
     */
    private TransformOptions(Set<String> transforms) {
        this.transforms = Set.copyOf(transforms);
    }

    /**
     * @param names raw names from the request
     * @return options with normalized names, without blanks and duplicates
     */
    public static TransformOptions fromNames(String[] names) {
        final Set<String> transformsFromNames = new HashSet<>();
        for (String name : names) {
            if (name.isBlank()) {
                continue;
            }
            transformsFromNames.add(name.strip().toLowerCase(Locale.ROOT));
        }
        return new TransformOptions(transformsFromNames);
    }

    /**
     * @param name lower-case transform name
     * @return {@code true} if the transform is enabled
     */
    public boolean isEnabled(String name) {
        return transforms.contains(name);
    }

    /**
     * @return unmodifiable set of enabled transform names
     */
    public Set<String> getTransforms() {
        return transforms;
    }
}
