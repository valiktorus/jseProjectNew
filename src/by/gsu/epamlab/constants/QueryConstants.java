package by.gsu.epamlab.constants;

public class QueryConstants {
    // ResultLoader
    public static final String SELECT_LOGIN_QUERY = "select logins.idlogin from logins where logins.name = ?;";
    public static final String INSERT_LOGIN_QUERY = "insert into logins (name) values (?);";
    public static final String SELECT_TEST_QUERY = "select tests.idTest from tests where tests.name = ?;";
    public static final String INSERT_TEST_QUERY = "insert into tests (name) values (?);";
    public static final String INSERT_RESULT_QUERY = "insert into results (results.loginId, results.testId, results.dat, results.mark) values(?,?,?,?);";
    public static final String TRUNCATE_TABLE_RESULTS = "truncate table results;";
    public static final String TRUNCATE_TABLE_LOGINS = "truncate table logins;";
    public static final String TRUNCATE_TABLE_TESTS = "truncate table tests;";
    //ResultLogic
    public static final String FIND_MEAN_MARKS ="select logins.name as name, avg(results.mark) as mark\n" +
            "from results\n" +
            "inner join logins on loginId = logins.idlogin\n" +
            "group by loginId\n" +
            "order by mark desc;";
    public static final String FIND_CURRENT_MONTH_RESULTS = "select logins.name as name, tests.name as test, results.dat, results.mark\n" +
            "from results\n" +
            "inner join logins on loginId = logins.idlogin\n" +
            "inner join tests on testId = tests.idTest\n" +
            "where month(dat) = month(now()) and year(dat) = year(now())\n" +
            "order by results.dat; ";
}
