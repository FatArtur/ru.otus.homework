package ru.otus.aop;

import ru.otus.TestLogging;
import ru.otus.TestLoggingInterface;
import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ioc {

    private Ioc(){
    }

    public static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;
        private final List<String> methods;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
            this.methods = Arrays.stream(myClass.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(m -> m.getName() + Arrays.toString(m.getParameterTypes()))
                    .collect(Collectors.toList());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var methodNameParam = method.getName() + Arrays.toString(method.getParameterTypes());
            if (methods.contains(methodNameParam)) {
                System.out.printf("executed method: %s, param: %s\n", method.getName(), Arrays.stream(args).toList());
            }
            return method.invoke(myClass, args);
        }
    }
}
