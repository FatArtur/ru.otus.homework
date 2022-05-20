package ru.otus;

import ru.otus.aop.Ioc;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface myClass = Ioc.createMyClass();
        myClass.calculation(6);
        myClass.calculation(6, 5);
        myClass.calculation(1, 3, 6);
    }
}
