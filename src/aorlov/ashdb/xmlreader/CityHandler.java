package aorlov.ashdb.xmlreader;

import aorlov.ashdb.core.geo.City;
import aorlov.ashdb.core.geo.Country;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class CityHandler extends DefaultHandler {
    private static Logger LOGGER = Logger.getLogger(CityHandler.class);

    private static final String TAG_CITY = "CITY";
    private static final String TAG_CITY_ID = "city_id";
    private static final String TAG_CITY_NAME = "name";
    private static final String TAG_COUNRY_ID = "country_id";
    private static final String TAG_REGION_ID = "region_id";


    private final Stack<String> tagsStack = new Stack<String>();
    private final StringBuilder tempVal = new StringBuilder();


    private List<City> cities;
    private City city;


    public List<City> getCities() {
        return cities;
    }


    @Override
    public void startElement(String uri, String localName,
                             String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        pushTag(qName);
        tempVal.setLength(0);

        if (qName.equalsIgnoreCase("rocid")) {
            cities = new ArrayList();
        } else if (qName.equalsIgnoreCase("city")) {
            city = new City();
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

        if (TAG_CITY_ID.equalsIgnoreCase(tag)) {
            if (TAG_CITY.equalsIgnoreCase(parentTag)) {
                int id = Integer.valueOf(tempVal.toString().trim());
                city.setCityId(id);
            }
        } else if (TAG_CITY_NAME.equalsIgnoreCase(tag)) {
            if (TAG_CITY.equalsIgnoreCase(parentTag)) {
                String name = tempVal.toString().trim();
                city.setName(name);
            }
        }else if(TAG_COUNRY_ID.equalsIgnoreCase(tag)){
            if(TAG_CITY.equals(parentTag)){
                int countryId = Integer.valueOf(tempVal.toString().trim());
                city.setCountryId(countryId);
            }
        }else if (TAG_CITY.equalsIgnoreCase(tag)) {
            cities.add(city);
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
