package by.timo.practice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SbRecordConstants {
    public static final Path PROJECT_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    public static final String ERROR_LOG = "error.log";
    public static final String STATISTICS = "statistics.txt";
    public static final int POST_INDEX = 0;
    public static final int ID_INDEX = 1;
    public static final int NAME_INDEX = 2;
    public static final int SALARY_INDEX = 3;
    public static final int UNIQUE_FIELD_INDEX = 4;
    public static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY);
}
