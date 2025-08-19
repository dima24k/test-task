package by.timo.practice.builder;

public interface Builder<T> {

    T build(String[] fields);
}
