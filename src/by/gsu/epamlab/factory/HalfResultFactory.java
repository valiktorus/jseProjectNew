package by.gsu.epamlab.factory;


import by.gsu.epamlab.beans.HalfResult;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.dao.IResultDAO;
import by.gsu.epamlab.dao.impl.ResultImplCsv;

import java.sql.Date;

public class HalfResultFactory extends ResultFactory {
    @Override
    public Result getResultFromFactory(String login, String test, Date date, int mark) {
        return new HalfResult(login, test, date, mark);
    }

    @Override
    public Result getResultFromFactory(String login, String test, String date, String mark) {
        return new HalfResult(login, test, date, mark);
    }

    @Override
    public IResultDAO getResultDaoFromFactory(String fileName) {
        return new ResultImplCsv(fileName, this);
    }

    @Override
    public double getAvgMark(double mark) {
        return mark / HalfResult.FACTOR;
    }
}
