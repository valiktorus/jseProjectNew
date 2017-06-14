package by.gsu.epamlab.beans;

import java.sql.Date;

public class HalfResult  extends Result{
    public static final int FACTOR = 2;

    public HalfResult(String login, String test, String date, String mark) {
        super(login, test, date, mark);
    }

    public HalfResult(String login, String test, Date date, int mark) {
        super(login, test, date, mark);
    }

    @Override
    public void setMark(String stringMark) {
        int mark = (int)(Double.parseDouble(stringMark) * FACTOR);
        setMark(mark);
    }

    @Override
    public String getStringMark() {
        int mark = getMark();
        return (mark/FACTOR + (mark % FACTOR == 0 ? "" : ".5"));
    }
}
