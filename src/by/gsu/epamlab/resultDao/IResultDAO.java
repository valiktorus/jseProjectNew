package by.gsu.epamlab.resultDao;

import by.gsu.epamlab.beans.Result;

public interface IResultDAO {
    Result nextResult();
    boolean hasResult();
    void close();
}
