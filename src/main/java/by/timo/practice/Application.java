package by.timo.practice;

import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.InputArgs;
import by.timo.practice.parser.ArgsParser;
import by.timo.practice.parser.SbRecordParser;
import by.timo.practice.reader.SbFileReader;
import by.timo.practice.writer.EmployeesWriter;
import by.timo.practice.writer.ErrorLogWriter;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        InputArgs inputArgs = ArgsParser.parseArgs(args);
        List<String> sbRecords = SbFileReader.readSbFiles();
        List<EmployeeBase> employees = SbRecordParser.parseSbRecords(sbRecords);
        List<String> errorLogLines = SbRecordParser.getErrorLogLines();

        EmployeesWriter.writeEmployees(inputArgs, employees, errorLogLines);
        ErrorLogWriter.writeRecordToErrorLog(errorLogLines, inputArgs.getOutputFilePath());
    }
}
