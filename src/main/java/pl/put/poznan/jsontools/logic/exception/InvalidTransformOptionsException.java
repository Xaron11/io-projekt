package pl.put.poznan.jsontools.logic.exception;

/**
 * Thrown when the requested transforms are unknown or cannot be used together.
 */
public class InvalidTransformOptionsException extends RuntimeException {
    /**
     * @param message error description returned to the client
     */
    public InvalidTransformOptionsException(String message) {
        super(message);
    }
}
