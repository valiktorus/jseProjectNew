package by.gsu.epamlab.resultDao.impl;

import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.SourceExceptions;
import by.gsu.epamlab.factory.ResultFactory;
import by.gsu.epamlab.handler.ResultHandler;
import by.gsu.epamlab.resultDao.IResultDAO;
import com.sun.org.apache.regexp.internal.RE;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Iterator;

public class ResultImplXml implements IResultDAO{
    private ResultFactory resultFactory;
    private Iterator<Result> resultIterator;

    public ResultImplXml(String fileName, ResultFactory resultFactory) throws SourceExceptions{
        final String FILE_EXT = ".xml";
        try {
            ResultHandler resultHandler = new ResultHandler(resultFactory);
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(resultHandler);
            reader.parse(Constants.SOURCE_DIR + fileName + FILE_EXT);
            resultIterator = resultHandler.getResults().iterator();
        } catch (Exception e) {
            throw new SourceExceptions(e.getMessage());
        }

    }

    @Override
    public Result nextResult() {
        return resultIterator.next();
    }

    @Override
    public boolean hasResult() {
        return resultIterator.hasNext();
    }

    @Override
    public void close() {
        resultIterator = null;
    }
}
