package by.timo.practice.model;

import by.timo.practice.util.InputArgsConstants;

import java.util.Objects;

import static by.timo.practice.validate.SbRecordValidator.isStringEmpty;

//добавить lombok
public class ArgKeyValue {
    private final String key;
    private final String value;

    public ArgKeyValue(String key, String value) {
        this.key = Objects.requireNonNull(key);
        this.value = value;
    }

    public static ArgKeyValue get(String arg) {
        if (isStringEmpty(arg)) {
            throw new IllegalArgumentException("Argument must not be null");
        }

        if (arg.equals(InputArgsConstants.STAT)) {
            return new ArgKeyValue(arg, null);
        }

        int eqIndex = arg.indexOf('=');

        if (eqIndex == arg.length() - 1 || eqIndex < 0) {
            throw new IllegalArgumentException("Missing value for argument: " + arg);
        }

        String key = arg.substring(0, eqIndex);
        String value = arg.substring(eqIndex + 1);

        return new ArgKeyValue(key, value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value != null ? key + "=" + value : key;
    }
}
