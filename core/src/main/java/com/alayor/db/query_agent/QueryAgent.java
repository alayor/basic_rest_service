package com.alayor.db.query_agent;

import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * Component used to interact with the database.
 */
public class QueryAgent {
    private static final Logger _logger = getLogger(QueryAgent.class.getName());

    private final transient DataSource _dataSource;
    private transient Connection _connection = null;

    public QueryAgent(DataSource dataSource) {
        _dataSource = dataSource;
    }

    /**
     * Creates a new connection to database
     *
     * @return The established connection.
     *
     */
    private Connection getConnection() {
        if (_connection == null) {
            try {
                _connection = _dataSource.getConnection();
            } catch (SQLException e) {
                _logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return _connection;
    }

    /**
     * Executes an insert statement.
     *
     * @param sql Insert sql query to be executed.
     * @return The generated key of the id column returned from the query execution.
     */
    public long executeInsert(String sql) throws Exception {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } finally {
            releaseConnectionIfPossible();
        }
    }

    /**
     * Executes a Select statement in the database and returns multiple rows.
     *
     * @param builder Helper object that contains placeholders and build method
     * @param <T>     Type of object to be returned as list and built by DbBuilder
     * @return The list of type T built from the select statement execution
     */
    public synchronized <T> List<Optional<T>> selectList(DbBuilder<T> builder) throws Exception {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(builder.sql())) {
            setValues(statement, builder.values());

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Optional<T>> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(builder.build(resultSet));
                }
                return results;
            }
        } finally {
            releaseConnectionIfPossible();
        }
    }

    /**
     * Executes a Select statement in the database and returns only one object
     *
     * @param <T>     Type of object to be returned and built by DbBuilder
     * @param builder Helper object that contains placeholders and build method
     * @return The type T built from the select statement execution
     */
    public synchronized <T> Optional<T> selectObject(DbBuilder<T> builder) throws Exception {
        try (PreparedStatement statement = getConnection().prepareStatement(builder.sql())) {
            setValues(statement, builder.values());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return builder.build(resultSet);
            }
        } finally {
            releaseConnectionIfPossible();
        }
    }

    private synchronized void releaseConnectionIfPossible() {
        if (_connection != null) {
            try {
                _connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error releasing connection");
            }
            _connection = null;
        }
    }

    private void setValues(PreparedStatement statement, Object... values) throws SQLException {
        if (values == null) {
            return;
        }
        for (int i = 0; i < values.length; i++) {
            Object obj = values[i];
            if (obj == null) {
                statement.setNull(i + 1, Types.NULL);
            } else if (obj instanceof String) {
                statement.setString(i + 1, (String) obj);
            } else if (obj instanceof Integer) {
                statement.setInt(i + 1, (Integer) obj);
            } else if (obj instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) obj);
            } else if (obj instanceof Double) {
                statement.setDouble(i + 1, (Double) obj);
            } else if (obj instanceof Long) {
                statement.setLong(i + 1, (Long) obj);
            } else if (obj instanceof String[]) {
                statement.setArray(i + 1, statement.getConnection().createArrayOf("varchar", (String[]) obj));
            } else {
                throw new RuntimeException("Unsupported SQL type for object : " + obj);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (_connection != null) {
            _connection.close();
            _connection = null;
        }
        super.finalize();
    }
}
