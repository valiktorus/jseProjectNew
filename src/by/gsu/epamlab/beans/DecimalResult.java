package by.gsu.epamlab.beans;

import java.sql.Date;

public class DecimalResult extends Result{
    public static final int FACTOR = 10;

    public DecimalResult(String login, String test, String date, String mark) {
        super(login, test, date, mark);
    }

    public DecimalResult(String login, String test, Date date, int mark) {
        super(login, test, date, mark);
    }

    @Override
    public void setMark(String stringMark) {
        setMark((int)(Double.parseDouble(stringMark) * FACTOR));
    }

    @Override
    public String getStringMark() {
        int mark = getMark();
        return String.format("%d.%d", mark / 10, mark % 10);
    }
}
