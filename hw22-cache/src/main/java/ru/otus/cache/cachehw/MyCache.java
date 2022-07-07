package ru.otus.cache.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listenerList = new ArrayList<>();;

    @Override
    public void put(K key, V value) {
        var getValue = cache.get(key);
        if (getValue == null) {
            cache.put(key, value);
            listenerNotify(key, value, "Put");
        } else {
            cache.replace(key, value);
            listenerNotify(key, value, "Replace");
        }
    }

    @Override
    public void remove(K key) {
        var removeValue = cache.remove(key);
        if (removeValue != null) {
            listenerNotify(key, removeValue, "Remove");
        }

    }

    @Override
    public V get(K key) {
        var getValue = cache.get(key);
        if (getValue != null) {
            listenerNotify(key, getValue, "Get");
        }
        return getValue;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);

    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);

    }

    private void listenerNotify(K key, V value, String action) {
        try {
            listenerList.forEach(lis -> lis.notify(key, value, action));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
