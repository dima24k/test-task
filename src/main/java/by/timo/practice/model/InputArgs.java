package by.timo.practice.model;

import by.timo.practice.model.enums.OrderType;
import by.timo.practice.model.enums.OutputType;
import by.timo.practice.model.enums.SortType;

import java.nio.file.Path;
import java.util.Objects;

public class InputArgs {
    private SortType sortType;
    private OrderType ascendingFlag;
    private OutputType outputType;
    private Path outputFilePath;
    private boolean stat;

    public InputArgs() {
    }

    public InputArgs(SortType sortType, OrderType ascending, OutputType outputType, Path outputFilePath, boolean stat) {
        this.sortType = sortType;
        this.ascendingFlag = ascending;
        this.outputType = outputType;
        this.outputFilePath = outputFilePath;
        this.stat = stat;
    }

    public SortType getSortField() {
        return sortType;
    }

    public void setSortField(SortType sortType) {
        this.sortType = sortType;
    }

    public OrderType isAscendingFlag() {
        return ascendingFlag;
    }

    public void setAscendingFlag(OrderType ascendingFlag) {
        this.ascendingFlag = ascendingFlag;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public Path getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(Path outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public boolean isStat() {
        return stat;
    }

    public void setStat(boolean stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "InputParameters{" +
                ", sortField=" + sortType +
                ", ascending=" + ascendingFlag +
                ", outputType=" + outputType +
                ", outputFilePath=" + outputFilePath +
                ", stat=" + stat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        InputArgs that = (InputArgs) o;
        return ascendingFlag == that.ascendingFlag &&
                stat == that.stat &&
                sortType == that.sortType &&
                outputType == that.outputType &&
                Objects.equals(outputFilePath, that.outputFilePath);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sortType);
        result = 31 * result + Objects.hashCode(ascendingFlag);
        result = 31 * result + Objects.hashCode(outputType);
        result = 31 * result + Objects.hashCode(outputFilePath);
        result = 31 * result + Boolean.hashCode(stat);
        return result;
    }
}
