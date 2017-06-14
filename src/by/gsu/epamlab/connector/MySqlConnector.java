package by.gsu.epamlab.connector;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.JdbcConstants;
import by.gsu.epamlab.enums.Driver;
import by.gsu.epamlab.exceptions.ConnectionException;

import java.sql.*;

public class MySqlConnector{
    private static final Connection CONNECTION = createdConnection();

    private MySqlConnector() {
    }

    private static Connection createdConnection(){
        try {
            Class.forName(Driver.MYSQL.getDriverName());
            return DriverManager.getConnection(JdbcConstants.DB_URL, JdbcConstants.user, JdbcConstants.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionException("Error getting connection");
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }

    public static void closeConnection(){
        try {
            if (CONNECTION != null){
                CONNECTION.close();
            }
        } catch (SQLException e) {
            System.err.println(Constants.ERROR_CLOSING_CONNECTION);
        }
    }

    public static void closeStatements(Statement... statements){
        for (Statement statement: statements) {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println(Constants.ERROR_CLOSING_STATEMENTS_SET + e.getMessage());
                }
            }
        }

    }

    public static void closeResultSet(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println(Constants.ERROR_CLOSING_RESULT_SET + e.getMessage());
            }
        }
    }

}
