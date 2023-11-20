package org.example.exceptions;

public class CsvDataExtractionFailed extends RuntimeException {

    /**
     *
     * @param message
     */
    public CsvDataExtractionFailed(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public CsvDataExtractionFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
