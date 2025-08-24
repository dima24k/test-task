package by.timo.practice.parser;

import by.timo.practice.model.EmployeeBase;
import by.timo.practice.parser.handler.RecordHandler;
import by.timo.practice.parser.handler.impl.EmployeeHandler;
import by.timo.practice.parser.handler.impl.ManagerHandler;
import by.timo.practice.type.PostType;
import by.timo.practice.util.SbRecordConstants;
import by.timo.practice.validate.SbRecordValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static by.timo.practice.type.PostType.EMPLOYEE;
import static by.timo.practice.type.PostType.MANAGER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SbRecordParser {
    @Getter
    private static final List<String> errorLogLines = new ArrayList<>();
    private static final Map<PostType, RecordHandler> HANDLERS = Map.of(
            MANAGER, new ManagerHandler(),
            EMPLOYEE, new EmployeeHandler()
    );

    public static List<EmployeeBase> parseSbRecords(List<String> sbRecords) {
        List<EmployeeBase> employees = new ArrayList<>();

        for (String line : sbRecords) {
            String[] fields = Arrays.stream(line.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            String cleaned = String.join(",", fields);

            PostType post = PostType.toPosition(fields[SbRecordConstants.POST_INDEX]);

            if (!SbRecordValidator.isSbRecordLengthValid(fields.length) ||
                    post == null) {
                errorLogLines.add(cleaned);
                continue;
            }

            RecordHandler handler = HANDLERS.get(post);
            handler.handle(fields, employees, errorLogLines, cleaned);
        }
        return employees;
    }
}