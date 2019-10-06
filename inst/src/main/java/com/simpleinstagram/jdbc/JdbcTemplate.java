package com.simpleinstagram.jdbc;

import com.simpleinstagram.exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class JdbcTemplate {
    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(PreparedStatementCreator preparedStatementCreator, PreparedStatementCallback<T> statementAction) {
        if (preparedStatementCreator == null) {
            throw new IllegalArgumentException("preparedStatementCreator object must not be null");
        }
        if (statementAction == null) {
            throw new IllegalArgumentException("statementAction object must not be null");
        }

        try (Connection conn = getDataSource().getConnection();
            PreparedStatement statement = preparedStatementCreator.createPreparedStatement(conn)) {
            return statementAction.doInPreparedStatement(statement);
        } catch (SQLException ex) {
            throw new DataAccessException("Fail to execute sql", ex);
        }
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
}
