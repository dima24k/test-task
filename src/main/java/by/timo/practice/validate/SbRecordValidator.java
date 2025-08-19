package by.timo.practice.validate;

import by.timo.practice.model.enums.PostType;
import by.timo.practice.validate.strategy.impl.EmployeeValidationStrategy;
import by.timo.practice.validate.strategy.impl.ManagerValidationStrategy;
import by.timo.practice.validate.strategy.ValidationStrategy;

import java.util.Map;
import java.util.Objects;

public final class SbRecordValidator {
    public static final Map<PostType, ValidationStrategy> strategies = Map.of(
            PostType.EMPLOYEE, new EmployeeValidationStrategy(),
            PostType.MANAGER, new ManagerValidationStrategy()
    );

    private static final String VALID_STRING_PATTERN = "^(?!\\s*$)[A-Za-z ]+$";
    private static final int EXPECTED_FIELDS_COUNT = 5;

    private SbRecordValidator() {}

    public static boolean isPostValid(PostType post) {
        return post.equals(PostType.MANAGER) || post.equals(PostType.EMPLOYEE);
    }

    public static boolean isSbRecordLengthValid(int sbRecordLength) {
        return sbRecordLength == EXPECTED_FIELDS_COUNT;
    }

    public static boolean isValidId(String id) {
        if (isStringEmpty(id)) {
            return false;
        }

        try {
            return Long.parseLong(id) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isStringValid(String value) {
        return Objects.nonNull(value) && value.matches(VALID_STRING_PATTERN);
    }

    public static boolean isSalaryValid(String salary) {
        if (isStringEmpty(salary)) {
            return false;
        }

        try {
            return Double.parseDouble(salary) > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
