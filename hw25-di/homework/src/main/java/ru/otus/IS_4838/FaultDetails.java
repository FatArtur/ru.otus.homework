package ru.otus.IS_4838;

/**
 * @author Andrey Sokolov
 */
public class FaultDetails {

    private int status;
    private String message;

    public FaultDetails() {
    }

    public FaultDetails(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
