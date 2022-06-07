package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEvenSecond implements Processor {

    private final DateTimeProvider provider;

    public ProcessorEvenSecond() {
        this.provider = LocalDateTime::now;
    }

    public ProcessorEvenSecond(DateTimeProvider dateTimeProvider) {
        this.provider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (provider.getDate().getSecond() % 2 == 0) {
            throw new RuntimeException("Process at even second.");
        }
        return message;
    }
}
