import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.connector.MySqlConnector;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.QueryConstants;
import by.gsu.epamlab.exceptions.ConnectionException;
import by.gsu.epamlab.exceptions.SourceExceptions;
import by.gsu.epamlab.factory.ResultFactory;
import by.gsu.epamlab.dao.IResultDAO;
import by.gsu.epamlab.resultLoader.ResultLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class RunnerLogic {
    public static void execute(ResultFactory resultFactory, String sourceName){
        try {
            loadResults(resultFactory, sourceName);
            printAverageMarks(resultFactory);
            getCurrentAndPrintLastDay(resultFactory);
        } finally {
            MySqlConnector.closeConnection();
        }
    }

    private static void loadResults(ResultFactory resultFactory, String sourceName){
        try {
            IResultDAO resultDAO = resultFactory.getResultDaoFromFactory(sourceName);
            ResultLoader.loadResults(resultDAO);
        } catch (SourceExceptions e) {
            System.err.println(Constants.ERROR_IN_SOURCE + e);
        } catch (ConnectionException e) {
            System.err.println(Constants.ERROR_IN_DB_CONNECTION + e);
        }
    }

    private static void printAverageMarks(ResultFactory resultFactory){
        System.out.println("Average marks:");
        final int LOGIN_COLUMN_INDEX = 1;
        final int AVG_MARK_COLUMN_INDEX = 2;
        Connection connection = MySqlConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QueryConstants.FIND_MEAN_MARKS);
            while (resultSet.next()){
                String login = resultSet.getString(LOGIN_COLUMN_INDEX);
                double mark = resultSet.getDouble(AVG_MARK_COLUMN_INDEX);
                System.out.printf("%s%s%.2f%n", login, Constants.DELIMITER, resultFactory.getAvgMark(mark));
            }
        } catch (SQLException e) {
            System.err.println(Constants.ERROR_AVERAGE_MARK + e.getMessage());
        }finally {
            MySqlConnector.closeResultSet(resultSet);
            MySqlConnector.closeStatements(statement);
        }
    }

    private static void getCurrentAndPrintLastDay(ResultFactory resultFactory){
        System.out.println("\nLast day results:");
        try {
            LinkedList<Result> currentMonthResults = getCurrentMonthResults(resultFactory);
            printLastDayResults(currentMonthResults);
        } catch (SQLException e) {
            System.err.println(Constants.ERROR_CURRENT_MONTH_RESULTS + e.getMessage());
        }
    }

    private static LinkedList<Result> getCurrentMonthResults(ResultFactory resultFactory) throws SQLException{
        final int LOGIN_COLUMN_INDEX = 1;
        final int TEST_COLUMN_INDEX = 2;
        final int DATE_COLUMN_INDEX = 3;
        final int MARK_COLUMN_INDEX = 4;
        Connection connection = MySqlConnector.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QueryConstants.FIND_CURRENT_MONTH_RESULTS);
            LinkedList<Result> currentMonthResults = new LinkedList<>();
            while (resultSet.next()){
                currentMonthResults.add(resultFactory.getResultFromFactory(
                        resultSet.getString(LOGIN_COLUMN_INDEX),
                        resultSet.getString(TEST_COLUMN_INDEX),
                        resultSet.getDate(DATE_COLUMN_INDEX),
                        resultSet.getInt(MARK_COLUMN_INDEX)));
            }
            return currentMonthResults;
        } finally {
            MySqlConnector.closeResultSet(resultSet);
            MySqlConnector.closeStatements(statement);
        }
    }

    private static void printLastDayResults(LinkedList<Result> results){
        Iterator<Result> resultDescendingIterator = results.descendingIterator();
        Date date = results.getLast().getDate();
        while (resultDescendingIterator.hasNext()){
            Result result = resultDescendingIterator.next();
            if (result.getDate().equals(date)){
                System.out.println(result);
            }else {
                break;
            }
        }
    }
}
