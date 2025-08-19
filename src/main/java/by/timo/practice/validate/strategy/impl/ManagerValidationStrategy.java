package by.timo.practice.validate.strategy.impl;

import by.timo.practice.util.SbRecordConstants;
import by.timo.practice.validate.SbRecordValidator;
import by.timo.practice.validate.strategy.ValidationStrategy;

public class ManagerValidationStrategy implements ValidationStrategy {
    @Override
    public boolean validate(String[] fields) {
        return SbRecordValidator.isValidId(fields[SbRecordConstants.ID_INDEX]) &&
                SbRecordValidator.isStringValid(fields[SbRecordConstants.NAME_INDEX]) &&
                SbRecordValidator.isSalaryValid(fields[SbRecordConstants.SALARY_INDEX]) &&
                SbRecordValidator.isStringValid(fields[SbRecordConstants.UNIQUE_FIELD_INDEX]);
    }
}
