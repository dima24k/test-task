package by.timo.practice.parser.strategy;

import by.timo.practice.model.InputArgs;

public interface ArgParserStrategy {
    void parse(String value, InputArgs inputArgs);
}
