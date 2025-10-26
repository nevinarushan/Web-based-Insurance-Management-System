
/**
 * This class is a custom exception that is thrown when there are no agents available to handle a booking request.
 */
package com.example.Insurance.Company.exception;

public class NoAgentsAvailableException extends Exception {
    public NoAgentsAvailableException(String message) {
        super(message);
    }
}
