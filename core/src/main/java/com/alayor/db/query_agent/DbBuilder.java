package com.alayor.db.query_agent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Class to build SQL queries using the provided information
 * @param <T>
 */
public abstract class DbBuilder<T>
{
    /**
     * String that represents the SQL to be executed.
     * @return SQL String.
     * @throws SQLException
     */
    public abstract String sql() throws SQLException;

    /**
     * Values that will be inserted in wildcards in the SQL string.
     * @return array with the values
     */
    public abstract Object[] values();

    /**
     * Method used to create the object after it's retrieved from the database.
     * @param resultSet that contains the information from the sql query execution.
     * @return The object built using the result set.
     * @throws SQLException
     */
    public abstract Optional<T> build(ResultSet resultSet) throws SQLException;
}
