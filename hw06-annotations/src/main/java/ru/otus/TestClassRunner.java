package ru.otus;

import ru.otus.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestClassRunner {
    private static final String AFTER_ANNOTATION_NAME = "@ru.otus.annotations.After()";
    private static final String BEFORE_ANNOTATION_NAME = "@ru.otus.annotations.Before()";
    private static final String TEST_ANNOTATION_NAME = "@ru.otus.annotations.Test()";

    public void run(Class<?> clazz){
        AtomicInteger passedTests = new AtomicInteger();
        AtomicInteger failedTests = new AtomicInteger();

        Method[] methodsAll = clazz.getDeclaredMethods();

        List<Method> methods = getMethods(methodsAll, TEST_ANNOTATION_NAME);
        methods.forEach(m -> {
            try {
                Object objectNew = ReflectionHelper.instantiate(clazz);
                ReflectionHelper.callMethod(objectNew, getMethods(methodsAll, BEFORE_ANNOTATION_NAME).get(0).getName());
                ReflectionHelper.callMethod(objectNew, m.getName());
                ReflectionHelper.callMethod(objectNew, getMethods(methodsAll, AFTER_ANNOTATION_NAME).get(0).getName());
                passedTests.getAndIncrement();
            } catch (Exception e){
                failedTests.getAndIncrement();
            }
        } );

        printResults(passedTests, failedTests);
    }

    private List<Method> getMethods(Method[] methods, String annotationName){
        return Arrays.stream(methods)
                .filter(s-> Arrays.asList(s.getDeclaredAnnotations()).toString().contains(annotationName))
                .collect(Collectors.toList());
    }

    private void printResults(AtomicInteger passed, AtomicInteger failed){
        System.out.println("*******************");
        System.out.println("Passed tests = " + passed.get());
        System.out.println("Failed tests = " + failed.get());
        System.out.println("Total tests = " + (passed.get() + failed.get()));
    }
}
