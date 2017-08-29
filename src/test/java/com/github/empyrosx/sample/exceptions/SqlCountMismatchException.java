package com.github.empyrosx.sample.exceptions;

/**
 * Created by empyros on 29.08.17.
 */
public class SqlCountMismatchException extends RuntimeException {

    public SqlCountMismatchException(String statement, int expectedCount, int actualCount) {
        super("Ожидалось " + statement + " запросов: " + expectedCount + ", реально: " + actualCount);
    }
}
