package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.strategy.ArgParserStrategy;
import by.timo.practice.type.OutputType;

public class OutputStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        OutputType outputType = OutputType.toOutputType(value);
        inputArgs.setOutputType(outputType);
    }
}
