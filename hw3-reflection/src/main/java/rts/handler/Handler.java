package rts.handler;

import rts.anntotations.After;
import rts.anntotations.Before;
import rts.anntotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Handler {

    public static void run(String className) {
        Class<?> cl = null;
        try {
            cl = Class.forName(className);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String, Integer> map = runTest(handlerBefore(cl), handlerTest(cl), handlerAfter(cl), cl);
        map.forEach((k, v) -> System.out.println(k + v));
    }

    private static List<Method> handlerBefore(Class<?> cl) {
        List<Method> beforeList = new ArrayList<>();
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(Before.class)) {
                beforeList.add(m);
            }
        }

        return beforeList;
    }

    private static List<Method> handlerAfter(Class<?> cl) {
        List<Method> aftereList = new ArrayList<>();
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(After.class)) {
                aftereList.add(m);
            }
        }

        return aftereList;
    }

    private static List<Method> handlerTest(Class<?> cl) {
        List<Method> testList = new ArrayList<>();
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {
                testList.add(m);
            }
        }

        return testList;
    }

    private static Map<String, Integer> runTest(List<Method> before, List<Method> test, List<Method> after, Class<?> cl) {
        Object classTest = null;
        int totalTestCount = test.size();

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("Total tests: ", totalTestCount);
        resultMap.put("Success tests: ", totalTestCount);
        resultMap.put("Error tests: ", 0);

        for (Method methodTest : test) {
            boolean testFailed = false;
            try {
                classTest = cl.getDeclaredConstructor().newInstance();
                for (Method methodBefore : before) {
                    methodBefore.setAccessible(true);
                    methodBefore.invoke(classTest);
                }
                methodTest.setAccessible(true);
                methodTest.invoke(classTest);

            } catch (Exception e) {
                testFailed = true;
                resultMap.put("Error tests: ", resultMap.get("Error tests: ") + 1);
                resultMap.put("Success tests: ", resultMap.get("Success tests: ") - 1);

            } finally {
                try {
                    for (Method methodAfter : after) {
                        methodAfter.setAccessible(true);
                        methodAfter.invoke(classTest);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!testFailed) {
                        resultMap.put("Error tests: ", resultMap.get("Error tests: ") + 1);
                        resultMap.put("Success tests: ", resultMap.get("Success tests: ") - 1);
                    }
                }
            }
        }
        return resultMap;
    }

}