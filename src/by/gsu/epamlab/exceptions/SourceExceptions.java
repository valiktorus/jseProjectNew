package by.gsu.epamlab.exceptions;


public class SourceExceptions extends RuntimeException {
    public SourceExceptions(String message) {
        super(message);
    }

    public SourceExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceExceptions(Throwable cause) {
        super(cause);
    }
}
