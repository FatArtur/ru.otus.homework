package ru.otus.IS_4838;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        try {
            check(true);
        } catch (final SubscriptionsWebFault e) {
            if (ResponseStatus.ERROR_NOT_FOUND_SUBSCRIBER.getStatus() == e.getDetails().getStatus()) {
//                System.out.println("Work__ " + e);
//                log.info("Work__", e);
                logger.info("Error_tut_ ",e);
            } else {
                throw e;
            }
        }
    }

    public static void check(boolean val){
        if (val == true) {
            final ResponseStatus st = ResponseStatus.ERROR_NOT_FOUND_SUBSCRIBER;
            final String msg = "Some message";
            throw new SubscriptionsWebFault(new FaultDetails(st.getStatus(), msg));
        }
    }
}
