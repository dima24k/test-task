package by.timo.practice.parser;

import by.timo.practice.model.ArgKeyValue;
import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.strategy.ArgParserStrategy;
import by.timo.practice.parser.strategy.ArgParserStrategyRegistry;
import by.timo.practice.type.ArgKey;
import by.timo.practice.validate.ArgsValidator;
import by.timo.practice.validate.SbRecordValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArgsParser {
    public static InputArgs parseArgs(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }

        InputArgs inputArgs = new InputArgs();
        Set<String> seenKeys = new HashSet<>();

        for (String arg : args) {
            if (SbRecordValidator.isStringNullOrEmpty(arg)) {
                throw new IllegalArgumentException("Argument cannot be null or empty");
            }

            ArgKeyValue kv = ArgKeyValue.get(arg);
            String key = kv.getKey();
            String value = kv.getValue();

            if (Objects.equals(key, ArgKey.SORT_SHORT.getKey())) {
                key = ArgKey.SORT_LONG.getKey();
            }

            if (Objects.equals(key, ArgKey.OUTPUT_SHORT.getKey())) {
                key = ArgKey.OUTPUT_LONG.getKey();
            }

            if (!seenKeys.add(key)) {
                throw new IllegalArgumentException("Duplicate argument: " + key);
            }

            ArgKey validKey = ArgKey.from(key);
            ArgParserStrategy strategy = ArgParserStrategyRegistry.getArgParserStrategy(validKey);
            strategy.parse(value, inputArgs);
        }

        ArgsValidator.validateArgs(
                inputArgs.isStat(),
                inputArgs.getOutputType(),
                inputArgs.getOutputFilePath(),
                inputArgs.getSortType(),
                inputArgs.getOrderType()
        );

        return inputArgs;
    }
}

