package ru.otus;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface{
    @Log
    @Override
    public void calculation(int param) {}

    @Override
    public void calculation(int param1, int param2){}

    @Log
    @Override
    public void calculation(int param1, int param2, int param3){}

    @Override
    public String toString() {
        return "TestLogging{}";
    }
}
