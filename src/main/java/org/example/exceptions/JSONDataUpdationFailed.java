package org.example.exceptions;

public class JSONDataUpdationFailed extends Exception {
    public JSONDataUpdationFailed(String message) {
        super(message);
    }
    public JSONDataUpdationFailed(String message, Throwable cause) {
        super(message, cause);
    }

}
