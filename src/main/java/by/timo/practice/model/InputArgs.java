package by.timo.practice.model;

import by.timo.practice.type.OrderType;
import by.timo.practice.type.OutputType;
import by.timo.practice.type.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InputArgs {
    private SortType sortType;
    private OrderType orderType;
    private OutputType outputType;
    private Path outputFilePath;
    private boolean stat;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        InputArgs that = (InputArgs) o;
        return orderType == that.orderType &&
                stat == that.stat &&
                sortType == that.sortType &&
                outputType == that.outputType &&
                Objects.equals(outputFilePath, that.outputFilePath);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sortType);
        result = 31 * result + Objects.hashCode(orderType);
        result = 31 * result + Objects.hashCode(outputType);
        result = 31 * result + Objects.hashCode(outputFilePath);
        result = 31 * result + Boolean.hashCode(stat);
        return result;
    }
}
