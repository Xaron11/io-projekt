package pl.put.poznan.jsontools.logic.exception;

/**
 * Thrown when the request body contains no JSON document (e.g. whitespace only).
 */
public class InvalidJsonException extends RuntimeException {
    /**
     * @param message error description returned to the client
     */
    public InvalidJsonException(String message) {
        super(message);
    }
}
