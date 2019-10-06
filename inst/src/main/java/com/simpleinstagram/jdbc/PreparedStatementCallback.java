package com.simpleinstagram.jdbc;

import com.simpleinstagram.exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementCallback<T> {
    T doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException;
}
