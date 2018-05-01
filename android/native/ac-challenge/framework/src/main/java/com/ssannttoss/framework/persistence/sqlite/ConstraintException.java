package com.ssannttoss.framework.persistence.sqlite;

/**
 * Constraint exception to describe invalid database values.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public class ConstraintException extends RuntimeException {
    private String table;
    private String column;
    private Object value;

    public ConstraintException(String table, String column, Object value, String message) {
        super(message);
        this.table = table;
        this.column = column;
        this.value = value;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }
}
