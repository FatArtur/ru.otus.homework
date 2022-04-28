package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.reflection.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestClassRunner {

    public void run(Class<?> clazz) {
        AtomicInteger passedTests = new AtomicInteger();
        AtomicInteger failedTests = new AtomicInteger();

        Method[] methodsAll = clazz.getDeclaredMethods();
        List<Method> methods = getMethods(methodsAll, Test.class);
        methods.forEach(m -> {
            Object objectNew = ReflectionHelper.instantiate(clazz);
            try {
                ReflectionHelper.callMethod(objectNew, getMethods(methodsAll, Before.class).get(0).getName());
                ReflectionHelper.callMethod(objectNew, m.getName());
                passedTests.getAndIncrement();
            } catch (Exception e) {
                failedTests.getAndIncrement();
            } finally {
                ReflectionHelper.callMethod(objectNew, getMethods(methodsAll, After.class).get(0).getName());
            }
        });

        printResults(passedTests, failedTests);
    }

    private List<Method> getMethods(Method[] methods, Class<? extends Annotation> annotation) {
        return Arrays.stream(methods)
                .filter(s -> s.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private void printResults(AtomicInteger passed, AtomicInteger failed) {
        System.out.println("*******************");
        System.out.println("Passed tests = " + passed.get());
        System.out.println("Failed tests = " + failed.get());
        System.out.println("Total tests = " + (passed.get() + failed.get()));
    }
}
