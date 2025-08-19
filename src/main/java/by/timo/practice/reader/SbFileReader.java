package by.timo.practice.reader;

import by.timo.practice.util.SbRecordConstants;
import by.timo.practice.exception.IOProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class SbFileReader {
    private SbFileReader() {}

    public static List<String> readSbFiles() {
        List<String> listLines = new ArrayList<>();

        try (Stream<Path> pathStream = Files.list(SbRecordConstants.PROJECT_DIRECTORY)) {
            pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".sb"))
                    .forEach(path -> processSbFile(path, listLines));
        } catch (IOException e) {
            throw new IOProcessingException("error traversing the project directory", e);
        }

        if (listLines.isEmpty()) {
            throw new IOProcessingException("no '.sb' files found in the project directory");
        }

        return listLines;
    }

    private static void processSbFile(Path path, List<String> listLines) {
        try {
            List<String> lines = Files.readAllLines(path);

            if (lines.isEmpty()) {
                throw new IOProcessingException("file " + path.getFileName() + " is empty");
            }

            listLines.addAll(lines);
        } catch (IOException e) {
            throw new IOProcessingException("error while reading file: " + path.getFileName(), e);
        }
    }
}
