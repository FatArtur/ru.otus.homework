package ru.otus.IS_4838;


/**
 * @author Andrey Sokolov
 */
public class SubscriptionsWebFault extends RuntimeException {

    private final FaultDetails details;

    public SubscriptionsWebFault(final FaultDetails details) {
        this.details = details;
    }

    public SubscriptionsWebFault(final String message) {
        this(message, null);
    }

    public SubscriptionsWebFault(final String message, final FaultDetails details) {
        super(message);
        this.details = details;
    }

    public SubscriptionsWebFault(final String message, final Throwable cause, final FaultDetails details) {
        super(message, cause);
        this.details = details;
    }

    public SubscriptionsWebFault(final Throwable cause, final FaultDetails details) {
        super(cause);
        this.details = details;
    }

    public FaultDetails getDetails() {
        return details;
    }
}
