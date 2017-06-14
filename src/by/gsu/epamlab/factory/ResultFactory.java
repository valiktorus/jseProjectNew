package by.gsu.epamlab.factory;

import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.resultDao.IResultDAO;
import by.gsu.epamlab.resultDao.impl.ResultImplCsv;

import java.sql.Date;

public class ResultFactory {
    public Result getResultFromFactory(String login, String test, Date date, int mark){
        return new Result(login, test, date, mark);
    }

    public Result getResultFromFactory(String login, String test, String date, String mark){
        return new Result(login, test, date, mark);
    }

    public IResultDAO getResultDaoFromFactory(String fileName){
        return new ResultImplCsv(fileName, this);
    }

    public double getAvgMark(double mark){
        return mark;
    }
}
