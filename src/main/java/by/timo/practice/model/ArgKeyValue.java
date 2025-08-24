package by.timo.practice.model;

import by.timo.practice.type.ArgKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class ArgKeyValue {
    private final String key;
    private final String value;

    public static ArgKeyValue get(String arg) {
        if (Objects.equals(ArgKey.STAT.getKey(), arg)) {
            return new ArgKeyValue(arg, null);
        }

        String statPrefix = ArgKey.STAT.getKey() + "=";
        if (arg.startsWith(statPrefix)) {
            throw new IllegalArgumentException("Argument " + ArgKey.STAT + " must not have a value");
        }

        int eqIndex = arg.indexOf('=');
        if (eqIndex <= 0 || eqIndex == arg.length() - 1) {
            throw new IllegalArgumentException("Invalid argument: " + arg);
        }

        String keyPart = arg.substring(0, eqIndex);
        String valuePart = arg.substring(eqIndex + 1);
        return new ArgKeyValue(keyPart, valuePart);
    }

    @Override
    public String toString() {
        return value != null
                ? key + "=" + value
                : key;
    }
}
