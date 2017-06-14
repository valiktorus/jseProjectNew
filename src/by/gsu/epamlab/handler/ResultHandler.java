package by.gsu.epamlab.handler;

import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.factory.ResultFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class ResultHandler extends DefaultHandler {
    private enum ResultsEnum {
        RESULTS, STUDENT, LOGIN, TESTS, TEST
    }

    private List<Result> results;
    private Result result;
    private String login;
    private ResultsEnum resultEnum;
    private ResultFactory resultFactory;

    public ResultHandler(ResultFactory resultFactory) {
        results = new LinkedList<>();
        this.resultFactory = resultFactory;
    }

    public List<Result> getResults() {
        return results;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        final int TEST_INDEX = 0;
        final int DATE_INDEX = 1;
        final int MARK_INDEX = 2;
        resultEnum = ResultsEnum.valueOf(localName.toUpperCase());
        if (resultEnum == ResultsEnum.TEST){
            result = resultFactory.getResultFromFactory(
                    login,
                    attributes.getValue(TEST_INDEX),
                    attributes.getValue(DATE_INDEX),
                    attributes.getValue(MARK_INDEX));
            results.add(result);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (resultEnum == ResultsEnum.LOGIN){
            String value = new String(ch, start, length).trim();
            if (!value.isEmpty()){
                login = value;
            }
        }
    }
}