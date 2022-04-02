package ru.otus;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.List;
import java.util.Set;

public class HelloOtus {
    public static void main(String... args) {
        List<String> letters = List.of("c", "c", "c", "c");
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b", 2);
        multiset.addAll(letters);
        multiset.remove("c");
        multiset.setCount("a", 1, 5);

        Set<Multiset.Entry<String>> setEntry = multiset.entrySet();
        setEntry.forEach(entry -> {
            System.out.println(entry.getElement() + ": " + entry.getCount());
        });
    }
}
