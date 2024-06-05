package com.sereneoasis.utils;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionUtils {

    public static Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toSet());
    }

}
