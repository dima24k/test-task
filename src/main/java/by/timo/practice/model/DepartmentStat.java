package by.timo.practice.model;

import java.math.BigDecimal;

public final class DepartmentStat {
    private final String department;
    private final BigDecimal min;
    private final BigDecimal max;
    private final BigDecimal avg;

    public DepartmentStat(String department, BigDecimal min, BigDecimal max, BigDecimal avg) {
        this.department = department;
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    @Override
    public String toString() {
        return department + ", " + min + ", " + max + ", " + avg;
    }

    public BigDecimal getMin() {
        return min;
    }

    public String getDepartment() {
        return department;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getAvg() {
        return avg;
    }
}