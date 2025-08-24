package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.strategy.ArgParserStrategy;
import by.timo.practice.type.OrderType;

public class OrderStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        OrderType orderType = OrderType.toOrder(value);
        inputArgs.setOrderType(orderType);
    }
}
