package com.sereneoasis.utils;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;
import java.util.stream.Collectors;

/***
 * Utilities to interact with the reflections library
 */
public class ReflectionUtils {

    public static Set<Class<?>> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toSet());
    }


    public static Set<Class<?>> findAllClasses(String packageName,  Class<?> superclass) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));

        return reflections.getSubTypesOf(superclass)
                .stream()
                .collect(Collectors.toSet());
    }

}
