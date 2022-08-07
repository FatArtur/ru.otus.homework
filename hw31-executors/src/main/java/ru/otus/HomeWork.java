package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);
    private String second = "2";
    private int count = 1;
    private boolean reverse = false;

    private synchronized void action(String message) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (second.equals(message)) {
                    this.wait();
                }
                logger.info("Поток-{} = {}", message, count);

                if ("2".equals(message)) {
                    if (count == 10) {
                        reverse = true;
                    } else if (count == 1) {
                        reverse = false;
                    }
                    if (reverse) {
                        count--;
                    } else {
                        count++;
                    }
                }
                second = message;
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        HomeWork hw = new HomeWork();
        new Thread(() -> hw.action("1")).start();
        new Thread(() -> hw.action("2")).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


}
