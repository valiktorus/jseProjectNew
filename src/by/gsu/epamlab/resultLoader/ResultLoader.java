package by.gsu.epamlab.resultLoader;

import by.gsu.epamlab.connector.MySqlConnector;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.QueryConstants;
import by.gsu.epamlab.exceptions.ConnectionException;
import by.gsu.epamlab.dao.IResultDAO;
import by.gsu.epamlab.beans.Result;

import java.sql.*;

public class ResultLoader {
    public static void loadResults(IResultDAO reader) throws ConnectionException{
        final int LOGIN_PARAMETER_INDEX = 1;
        final int TEST_PARAMETER_INDEX = 2;
        final int DATE_PARAMETER_INDEX = 3;
        final int MARK_PARAMETER_INDEX = 4;
        Connection connection = MySqlConnector.getConnection();
        PreparedStatement psSelectLogin = null;
        PreparedStatement psInsertLogin = null;
        PreparedStatement psSelectTest = null;
        PreparedStatement psInsertTest = null;
        PreparedStatement psInsertResult = null;
        try {
            cleanDb(connection);
            psSelectLogin = connection.prepareStatement(QueryConstants.SELECT_LOGIN_QUERY);
            psInsertLogin = connection.prepareStatement(QueryConstants.INSERT_LOGIN_QUERY);
            psSelectTest = connection.prepareStatement(QueryConstants.SELECT_TEST_QUERY);
            psInsertTest = connection.prepareStatement(QueryConstants.INSERT_TEST_QUERY);
            psInsertResult = connection.prepareStatement(QueryConstants.INSERT_RESULT_QUERY);
                while (reader.hasResult()){
                    Result result = reader.nextResult();
                    String login = result.getLogin();
                    String test = result.getTest();
                    int idLogin = getId(login, psSelectLogin, psInsertLogin);
                    int idTest = getId(test, psSelectTest, psInsertTest);
                    psInsertResult.setInt(LOGIN_PARAMETER_INDEX, idLogin);
                    psInsertResult.setInt(TEST_PARAMETER_INDEX, idTest);
                    psInsertResult.setDate(DATE_PARAMETER_INDEX, result.getDate());
                    psInsertResult.setInt(MARK_PARAMETER_INDEX, result.getMark());
                    psInsertResult.executeUpdate();
                }
        } catch (SQLException e) {
            throw new ConnectionException(Constants.ERROR_LOAD + e.getMessage());
        } finally {
            reader.close();
            MySqlConnector.closeStatements(psInsertLogin,psSelectLogin,psInsertTest,psSelectTest, psInsertResult);
        }
    }

    private static int getId(String element, PreparedStatement psSelectElement, PreparedStatement psInsertElement) throws SQLException{
        final int FIRST_QUERY_PARAMETER_INDEX = 1;
        final int ID_INDEX = 1;
        ResultSet resultSet = null;
        try {
            psSelectElement.setString(FIRST_QUERY_PARAMETER_INDEX, element);
            resultSet = psSelectElement.executeQuery();
            if (!resultSet.next()){
                psInsertElement.setString(FIRST_QUERY_PARAMETER_INDEX, element);
                psInsertElement.executeUpdate();
                resultSet = psSelectElement.executeQuery();
                resultSet.next();
            }
            return resultSet.getInt(ID_INDEX);
        } finally {
            MySqlConnector.closeResultSet(resultSet);
        }
    }

    private static void cleanDb(Connection connection){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(QueryConstants.TRUNCATE_TABLE_RESULTS);
            statement.executeUpdate(QueryConstants.TRUNCATE_TABLE_LOGINS);
            statement.executeUpdate(QueryConstants.TRUNCATE_TABLE_TESTS);
        } catch (SQLException e) {
            System.err.println(Constants.ERROR_CLEANING_DB);
        }
    }
}
