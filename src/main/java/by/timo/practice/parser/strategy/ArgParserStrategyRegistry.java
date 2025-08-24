package by.timo.practice.parser.strategy;

import by.timo.practice.parser.strategy.impl.OrderStrategy;
import by.timo.practice.parser.strategy.impl.OutputStrategy;
import by.timo.practice.parser.strategy.impl.PathStrategy;
import by.timo.practice.parser.strategy.impl.SortStrategy;
import by.timo.practice.parser.strategy.impl.StatStrategy;
import by.timo.practice.type.ArgKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArgParserStrategyRegistry {
    private static final Map<ArgKey, ArgParserStrategy> PARSER_STRATEGY_MAP = Map.of(
            ArgKey.SORT_LONG, new SortStrategy(),
            ArgKey.SORT_SHORT, new SortStrategy(),
            ArgKey.STAT, new StatStrategy(),
            ArgKey.OUTPUT_LONG, new OutputStrategy(),
            ArgKey.OUTPUT_SHORT, new OutputStrategy(),
            ArgKey.ORDER, new OrderStrategy(),
            ArgKey.PATH, new PathStrategy()
    );

    public static ArgParserStrategy getArgParserStrategy(ArgKey key) {
        return PARSER_STRATEGY_MAP.get(key);
    }
}
