package rts.handler;

import rts.anntotations.After;
import rts.anntotations.Before;
import rts.anntotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Handler {

    public void run(String className){
        Class cl = null;
        try {
            cl = Class.forName(className);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String,Integer> map = runTest(handlerBefore(cl), handlerTest(cl), handlerAfter(cl));
        map.forEach((k, v) -> System.out.println(k + v));

    }

    private Map<Method, Class> handlerBefore(Class cl) {
        HashMap<Method, Class> beforeMap = new HashMap<>();
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(Before.class)) {
                beforeMap.put(m, cl);
            }
        }

        return beforeMap;
    }

    private Map<Method, Class> handlerAfter(Class cl) {
        HashMap<Method, Class> aftereMap = new HashMap<>();
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(After.class)) {
                aftereMap.put(m, cl);
            }
        }

        return aftereMap;
    }

    private Map<Method, Class> handlerTest(Class cl) {
        HashMap<Method, Class> testMap = new HashMap<>();
        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {
                testMap.put(m, cl);
            }
        }

        return testMap;
    }

    private Map<String, Integer> runTest(Map<Method, Class> before, Map<Method, Class> test, Map<Method, Class> after) {
        Object classTest = null;
        Method methodTest = null;
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("Total tests: ", 0);
        resultMap.put("Success tests: ", 0);
        resultMap.put("Error tests: ", 0);

        for (Map.Entry<Method, Class> entryTest : test.entrySet()) {
            try {
                methodTest = entryTest.getKey();
                classTest = entryTest.getValue().getDeclaredConstructor().newInstance();

                for (Map.Entry<Method, Class> entryBefore : before.entrySet()) {
                    entryBefore.getKey().setAccessible(true);
                    entryBefore.getKey().invoke(classTest);
                }
                methodTest.setAccessible(true);
                methodTest.invoke(classTest);
                resultMap.put("Total tests: ", resultMap.get("Total tests: ") + 1);

                for (Map.Entry<Method, Class> entryAfter : after.entrySet()) {
                    entryAfter.getKey().setAccessible(true);
                    entryAfter.getKey().invoke(classTest);
                }

            } catch (Exception e) {
                resultMap.put("Error tests: ", resultMap.get("Error tests: ") + 1);
                resultMap.put("Total tests: ", resultMap.get("Total tests: ") + 1);
            }
        }
        int success = resultMap.get("Total tests: ") - resultMap.get("Error tests: ");
        resultMap.put("Success tests: ", success);
        return resultMap;
    }

}