package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorChangeFields implements Processor {

    @Override
    public Message process(Message message) {
        String message11 = message.getField11();
        return message.toBuilder().field11(message.getField12()).field12(message11).build();
    }
}
