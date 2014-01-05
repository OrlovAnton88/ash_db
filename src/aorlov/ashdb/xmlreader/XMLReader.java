package aorlov.ashdb.xmlreader;

import aorlov.ashdb.core.geo.City;
import aorlov.ashdb.core.geo.Country;
import aorlov.ashdb.util.FileName;
import org.apache.log4j.Logger;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLReader {
    private static Logger LOGGER = Logger.getLogger(XMLReader.class);

    private static XMLReader instance;


    private XMLReader() {

    }


    public Map<String, City> getCityMap() {
        Map cityMap = new HashMap<String, City>();
        List<City> cities = getCities();
        for (City city : cities) {
            cityMap.put(city.getName(), city);
        }

        return cityMap;
    }


    public List<City> getCities() {
        SAXParser saxParser = null;
        List<City> cities = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
            CityHandler handler = new CityHandler();
            saxParser.parse(FileName.GEO_XML, handler);

            cities = handler.getCities();

            for (City city : cities) {
                LOGGER.info(city.toString());
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return cities;
    }


    public void getCountries() {
        SAXParser saxParser = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
            CountryHandler handler = new CountryHandler();
            saxParser.parse(FileName.GEO_XML, handler);

            List<Country> counties = handler.getCountries();

            for (Country country : counties) {
                LOGGER.info(country.toString());
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }


    public static XMLReader getInstance() {
        if (instance == null) {
            instance = new XMLReader();
        }
        return instance;
    }

}
