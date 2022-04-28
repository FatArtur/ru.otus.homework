package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClass {
    @Before
    public void init(){
        System.out.println("Run before test! for: " + this);
    }

    @After
    public void tearDown(){
        System.out.println("Run after test! for: " + this);
    }

    @Test
    public void testOne(){
        System.out.println("Execution testOne! for: " + this);
    }

    @Test
    public void testTwo(){
        System.out.println("Execution testTwo! for: " + this);
    }

    @Test
    public void testThree(){
        throw new  RuntimeException("Execution testTwo with Exception!");
    }

    @Test
    public void testFour(){
        System.out.println("Execution testFour! for: " + this);
    }
}
