package by.gsu.epamlab.dao.impl;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.SourceExceptions;
import by.gsu.epamlab.factory.ResultFactory;
import by.gsu.epamlab.dao.IResultDAO;
import by.gsu.epamlab.beans.Result;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

public class ResultImplCsv implements IResultDAO {
    private Scanner scanner;
    private ResultFactory resultFactory;

    public ResultImplCsv(String fileName, ResultFactory resultFactory) throws SourceExceptions{
        final String FILE_EXT = ".csv";
        try {
            this.scanner = new Scanner(new FileReader(Constants.SOURCE_DIR + fileName + FILE_EXT));
            scanner.useLocale(Locale.ENGLISH);
            this.resultFactory = resultFactory;
        } catch (FileNotFoundException e) {
            throw new SourceExceptions(e.getMessage());
        }
    }

    @Override
    public Result nextResult() {
        String line = scanner.nextLine();
        String[] arrayLine = line.split(";");
        return resultFactory.getResultFromFactory(arrayLine[0], arrayLine[1], arrayLine[2], arrayLine[3]);
    }

    @Override
    public boolean hasResult() {
        return scanner.hasNextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
