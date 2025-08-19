package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.strategy.ArgParserStrategy;

public class StatStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        inputArgs.setStat(true);
    }
}
