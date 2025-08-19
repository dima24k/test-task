package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.model.enums.OutputType;
import by.timo.practice.parser.strategy.ArgParserStrategy;

public class OutputStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        OutputType outputType = OutputType.toOutputType(value);
        inputArgs.setOutputType(outputType);
    }
}
