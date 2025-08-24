package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.strategy.ArgParserStrategy;
import by.timo.practice.type.SortType;

public class SortStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        SortType sortType = SortType.toSortField(value);
        inputArgs.setSortType(sortType);
    }
}
