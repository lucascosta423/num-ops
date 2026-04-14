package com.main.numOps.utils;

@FunctionalInterface
public interface FileMapper<T> {
    void map(T instance, String header,String values);
}
