package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.model.enums.OrderType;
import by.timo.practice.parser.strategy.ArgParserStrategy;

public class OrderStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        OrderType ascending = OrderType.toOrder(value);
        inputArgs.setAscendingFlag(ascending);
    }
}
