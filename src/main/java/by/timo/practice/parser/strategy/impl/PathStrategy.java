package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.strategy.ArgParserStrategy;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class PathStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        try {
            inputArgs.setOutputFilePath(Path.of(value));
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Invalid path: " + value, e);
        }
    }
}
