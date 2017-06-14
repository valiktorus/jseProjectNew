package by.gsu.epamlab.factory;

import by.gsu.epamlab.beans.DecimalResult;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.resultDao.IResultDAO;
import by.gsu.epamlab.resultDao.impl.ResultImplXml;

import java.sql.Date;

public class DecimalResultFactory extends ResultFactory {
    @Override
    public Result getResultFromFactory(String login, String test, Date date, int mark) {
        return new DecimalResult(login, test, date, mark);
    }

    @Override
    public Result getResultFromFactory(String login, String test, String date, String mark) {
        return new DecimalResult(login, test, date, mark);
    }

    @Override
    public IResultDAO getResultDaoFromFactory(String fileName) {
        return new ResultImplXml(fileName, this);
    }

    @Override
    public double getAvgMark(double mark) {
        return mark / DecimalResult.FACTOR;
    }
}
