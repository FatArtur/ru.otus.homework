package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> map;

    public HistoryListener() {
        this.map = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        map.put(msg.getId(), msg.toBuilder().field13(new ObjectForMessage(msg.getField13())).build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(map.get(id));
    }
}
