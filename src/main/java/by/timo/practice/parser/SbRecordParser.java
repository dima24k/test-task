package by.timo.practice.parser;

import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.enums.PostType;
import by.timo.practice.parser.handler.RecordHandler;
import by.timo.practice.parser.handler.impl.EmployeeHandler;
import by.timo.practice.parser.handler.impl.ManagerHandler;
import by.timo.practice.validate.SbRecordValidator;
import by.timo.practice.util.SbRecordConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static by.timo.practice.model.enums.PostType.EMPLOYEE;
import static by.timo.practice.model.enums.PostType.MANAGER;

public final class SbRecordParser {
    private static final List<String> errorLogLines = new ArrayList<>();
    private static final Map<PostType, RecordHandler> HANDLERS = Map.of(
            MANAGER, new ManagerHandler(),
            EMPLOYEE, new EmployeeHandler()
    );

    private SbRecordParser() {
    }

    public static List<EmployeeBase> parseSbRecords(List<String> sbRecords) {
        List<EmployeeBase> employees = new ArrayList<>();

        for (String line : sbRecords) {
            String[] fields = Arrays.stream(line.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            String cleaned = String.join(",", fields);
            PostType post = PostType.toPosition(fields[SbRecordConstants.POST_INDEX]);

            if (!SbRecordValidator.isSbRecordLengthValid(fields.length) ||
                    !SbRecordValidator.isPostValid(post)) {
                errorLogLines.add(cleaned);
                continue;
            }

            RecordHandler handler = HANDLERS.get(post);
            handler.handle(fields, employees, errorLogLines, cleaned);
        }
        return employees;
    }

    public static List<String> getErrorLogLines() {
        return errorLogLines;
    }
}