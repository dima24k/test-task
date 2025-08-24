package by.timo.practice.validate;

import by.timo.practice.type.OrderType;
import by.timo.practice.type.OutputType;
import by.timo.practice.type.SortType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArgsValidator {
    public static void validateArgs(
            boolean stat,
            OutputType output,
            Path path,
            SortType sort,
            OrderType order) {
        if (argumentsOutputPathWithoutStat(stat, output, path)) {
            throw new IllegalArgumentException("arguments --output, -o, --path is only allowed with --stat");
        }
        if (argumentOutputFileWithoutPath(output, path)) {
            throw new IllegalArgumentException("argument --output=file requires --path");
        }
        if (argumentPathWithoutOutputFile(output, path)) {
            throw new IllegalArgumentException("argument --path is only valid when --output=file or -o=file");
        }
        if (argumentOrderWithoutSort(sort, order)) {
            throw new IllegalArgumentException("argument --order can't be used without sort argument");
        }
    }

    private static boolean argumentsOutputPathWithoutStat(boolean stat, OutputType output, Path path) {
        return !stat && (output != null || path != null);
    }

    private static boolean argumentOutputFileWithoutPath(OutputType output, Path path) {
        return Objects.equals(output, OutputType.FILE) && path == null;
    }

    private static boolean argumentPathWithoutOutputFile(OutputType output, Path path) {
        return path != null && !Objects.equals(output, OutputType.FILE);
    }

    private static boolean argumentOrderWithoutSort(SortType sort, OrderType order) {
        return order != null && sort == null;
    }
}
