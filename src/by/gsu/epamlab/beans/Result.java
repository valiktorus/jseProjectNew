package by.gsu.epamlab.beans;

import by.gsu.epamlab.constants.Constants;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Result {

    private final static SimpleDateFormat GET_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private final static SimpleDateFormat SET_DATE_FORMAT = new SimpleDateFormat("yy-MM-dd");
    private String login;
    private String test;
    private Date date;
    private int mark;

    public Result(String login, String test, String date, String mark){
        this.login = login;
        this.test = test;
        setDate(date);
        setMark(mark);
    }

    public Result(String login, String test, Date date, int mark) {
        this.login = login;
        this.test = test;
        this.date = date;
        this.mark = mark;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        return GET_DATE_FORMAT.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String dateString) {
        try {
            date = new Date(SET_DATE_FORMAT.parse(dateString).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(Constants.ERROR_PARSING_DATE);
        }
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setMark(String stringMark){
        mark = Integer.parseInt(stringMark);
    }

    public String getStringMark(){
        Integer mark = this.mark;
        return  mark.toString();
    }

    @Override
    public String toString() {
        return login + Constants.DELIMITER + test + Constants.DELIMITER + getStringDate() + Constants.DELIMITER + getStringMark();
    }
}
