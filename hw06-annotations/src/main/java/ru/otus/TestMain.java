package ru.otus;

public class TestMain {
    public static void main(String[] args) {
        TestClassRunner tests = new TestClassRunner();
        tests.run(TestClass.class);
    }
}
