package aorlov.ashdb.xmlreader;

import aorlov.ashdb.core.geo.Country;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class CountryHandler extends DefaultHandler {
    private static Logger LOGGER = Logger.getLogger(CountryHandler.class);

    private static final String TAG_COUNTRY = "country";
    private static final String TAG_COUNTRY_ID = "country_id";
    private static final String TAG_COUNTRY_NAME = "name";

    private final Stack<String> tagsStack = new Stack<String>();
    private final StringBuilder tempVal = new StringBuilder();


    private List<Country> countries;
    private Country country;


    public List<Country> getCountries() {
        return countries;
    }


    @Override
    public void startElement(String uri, String localName,
                             String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        pushTag(qName);
        tempVal.setLength(0);

        if (qName.equalsIgnoreCase("rocid")) {
            countries = new ArrayList();
        } else if (qName.equalsIgnoreCase("country")) {
            LOGGER.debug("Country in");
            country = new Country();
        }

    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String tag = peekTag();
        if (!qName.equals(tag)) {
            throw new InternalError();
        }
        popTag();
        String parentTag = peekTag();

        if (TAG_COUNTRY_ID.equalsIgnoreCase(tag)) {

            int id = Integer.valueOf(tempVal.toString().trim());

            if (TAG_COUNTRY.equalsIgnoreCase(parentTag)) {
                country.setCountryId(id);
            }
        } else if (TAG_COUNTRY_NAME.equalsIgnoreCase(tag)) {
            String name = tempVal.toString().trim();
            if (TAG_COUNTRY.equalsIgnoreCase(parentTag)) {
                country.setName(name);
            }
        } else if (TAG_COUNTRY.equalsIgnoreCase(tag)) {
            countries.add(country);
        }

    }


    public void characters(char ch[], int start, int length) throws SAXException {
        tempVal.append(ch, start, length);

    }

    private void pushTag(String tag) {
        tagsStack.push(tag);
    }

    private String popTag() {
        return tagsStack.pop();
    }

    private String peekTag() {
        return tagsStack.peek();
    }

    public void startDocument() {
        pushTag("");
    }


}
