package by.timo.practice.writer;

import by.timo.practice.exception.IOProcessingException;
import by.timo.practice.util.EmployeeWriterUtil;
import by.timo.practice.util.SbRecordConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public final class ErrorLogWriter {
    private ErrorLogWriter() {}

    public static void writeRecordToErrorLog(List<String> lines, Path path) {
        if (lines == null || lines.isEmpty()) {
            return;
        }

        Path baseDir = EmployeeWriterUtil.getBaseDir(path, SbRecordConstants.PROJECT_DIRECTORY);
        Path file = baseDir.resolve(SbRecordConstants.ERROR_LOG);
        EmployeeWriterUtil.ensureParentDirs(file);

        try {
            Files.write(
                    file,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new IOProcessingException("Failed to write error log: " + file, e);
        }
    }
}

