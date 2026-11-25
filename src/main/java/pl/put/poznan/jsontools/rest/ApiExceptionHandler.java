package pl.put.poznan.jsontools.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.put.poznan.jsontools.logic.exception.InvalidJsonException;
import pl.put.poznan.jsontools.logic.exception.InvalidTransformOptionsException;

import java.util.Map;

/**
 * Turns client errors into {@code 400} responses of the form {@code {"error": "..."}}.
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * @param e JSON parsing error
     * @return 400 response
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(JsonProcessingException e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid JSON: " + e.getOriginalMessage()));
    }

    /**
     * @param e invalid transform options
     * @return 400 response
     */
    @ExceptionHandler(InvalidTransformOptionsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTransformOption(InvalidTransformOptionsException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    /**
     * @param e empty request body
     * @return 400 response
     */
    @ExceptionHandler(InvalidJsonException.class)
    public ResponseEntity<Map<String, String>> handleEmptyJson(InvalidJsonException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
