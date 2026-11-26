package pl.put.poznan.jsontools.logic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TransformOptionsTest {
    @Test
    void normalizesNames() {
        String[] testCases = {" Minify", "", " ", "pretty", "minify"};
        TransformOptions options = TransformOptions.fromNames(testCases);
        assertThat(options.getTransforms()).containsExactlyInAnyOrder("minify", "pretty");
    }

    @Test
    void emptyArrayGivesNoTransforms() {
        String[] testCases = {};
        TransformOptions options = TransformOptions.fromNames(testCases);
        assertThat(options.getTransforms()).isEmpty();
    }

    @Test
    void isEnabledReportsOnlyGivenNames() {
        String[] testCases = {"minify"};
        TransformOptions options = TransformOptions.fromNames(testCases);
        assertThat(options.isEnabled("minify")).isTrue();
        assertThat(options.isEnabled("pretty")).isFalse();
    }

    @Test
    void transformsCannotBeModified() {
        TransformOptions options = TransformOptions.fromNames(new String[]{"minify"});

        assertThatThrownBy(() -> options.getTransforms().add("pretty"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
