package com.main.numOps.mapper;

@FunctionalInterface
public interface FileMapper<T> {
    void map(T instance, String header,String values);
}
