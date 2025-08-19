package by.timo.practice.parser.strategy.impl;

import by.timo.practice.model.InputArgs;
import by.timo.practice.model.enums.SortType;
import by.timo.practice.parser.strategy.ArgParserStrategy;

public class SortStrategy implements ArgParserStrategy {
    @Override
    public void parse(String value, InputArgs inputArgs) {
        SortType sortType = SortType.toSortField(value);
        inputArgs.setSortField(sortType);
    }
}
