package by.timo.practice.parser;

import by.timo.practice.model.InputArgs;
import by.timo.practice.model.enums.OrderType;
import by.timo.practice.model.enums.OutputType;
import by.timo.practice.model.enums.SortType;
import by.timo.practice.parser.strategy.ArgParserStrategy;
import by.timo.practice.parser.strategy.impl.OrderStrategy;
import by.timo.practice.parser.strategy.impl.OutputStrategy;
import by.timo.practice.parser.strategy.impl.PathStrategy;
import by.timo.practice.parser.strategy.impl.SortStrategy;
import by.timo.practice.parser.strategy.impl.StatStrategy;
import by.timo.practice.model.ArgKeyValue;
import by.timo.practice.util.InputArgsConstants;
import by.timo.practice.validate.SbRecordValidator;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class ArgsParser {
    private static final Map<String, ArgParserStrategy> argParsersStrategy = Map.of(
            InputArgsConstants.SORT_LONG, new SortStrategy(),
            InputArgsConstants.SORT_SHORT, new SortStrategy(),
            InputArgsConstants.STAT, new StatStrategy(),
            InputArgsConstants.OUTPUT_LONG, new OutputStrategy(),
            InputArgsConstants.OUTPUT_SHORT, new OutputStrategy(),
            InputArgsConstants.ORDER, new OrderStrategy(),
            InputArgsConstants.PATH, new PathStrategy()
    );

    private ArgsParser() {}

    public static InputArgs parseArgs(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }

        InputArgs inputArgs = new InputArgs();

        for (String arg : args) {
            if (SbRecordValidator.isStringEmpty(arg)) {
                throw new IllegalArgumentException("Argument cannot be null or empty");
            }

            ArgKeyValue keyValue = ArgKeyValue.get(arg);
            String key = keyValue.getKey();
            String value = keyValue.getValue();

            ArgParserStrategy strategy = argParsersStrategy.get(key);

            if (strategy == null) {
                throw new IllegalArgumentException("Unknown argument: " + key);
            }

            strategy.parse(value, inputArgs);
        }

        validateArgs(
                inputArgs.isStat(),
                inputArgs.getOutputType(),
                inputArgs.getOutputFilePath(),
                inputArgs.getSortField(),
                inputArgs.isAscendingFlag()
        );

        return inputArgs;
    }

    private static void validateArgs(boolean stat, OutputType output,
                                     Path path, SortType sort, OrderType ascending) {
        if (!stat && (output != null || path != null)) {
            throw new IllegalArgumentException("arguments --output, -o, --path is only allowed with --stat");
        }
        if (Objects.equals(output, OutputType.FILE) && path == null) {
            throw new IllegalArgumentException("argument --output=file requires --path");
        }
        if (path != null && !Objects.equals(output, OutputType.FILE)) {
            throw new IllegalArgumentException("argument --path is only valid when --output=file or -o=file");
        }
        if (ascending != null && sort == null) {
            throw new IllegalArgumentException("argument --order can't be used without sort argument");
        }
    }
}
