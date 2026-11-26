package pl.put.poznan.jsontools.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.put.poznan.jsontools.app.JsonToolsApplication;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JsonToolsApplication.class)
@AutoConfigureMockMvc
class JsonToolsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions postJson(String url, String body) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }

    @Test
    void returnsCompactJsonWithoutOptions() throws Exception {
        postJson("/api/json", "{ \"a\" : 1, \"b\" : [1, 2] }")
            .andExpect(status().isOk())
            .andExpect(content().string("{\"a\":1,\"b\":[1,2]}"));
    }

    @Test
    void rejectsInvalidJson() throws Exception {
        postJson("/api/json", "{\"a\": ")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error", startsWith("Invalid JSON")));
    }

    @Test
    void rejectsContentAfterJsonDocument() throws Exception {
        postJson("/api/json", "{\"a\": 1}}")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error", startsWith("Invalid JSON")));
    }

    @Test
    void rejectsWhitespaceOnlyBody() throws Exception {
        postJson("/api/json", "   ")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Request body is empty"));
    }

    @Test
    void rejectsUnknownTransform() throws Exception {
        postJson("/api/json?transforms=abc", "{\"a\": 1}")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Unknown transforms: abc"));
    }
}
