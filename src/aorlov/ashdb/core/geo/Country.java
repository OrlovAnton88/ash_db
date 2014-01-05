package aorlov.ashdb.core.geo;

/**
 * Created with IntelliJ IDEA.
 * User: Mouse
 * Date: 31.12.13
 * Time: 0:59
 * To change this template use File | Settings | File Templates.
 */
public class Country {
    private int countryId;
    private int city_id;
    private String name;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        StringBuffer toReturn = new StringBuffer();
        toReturn.append("Country [ id: ");
        toReturn.append(countryId);
        toReturn.append(", name: ");
        toReturn.append(name);
        toReturn.append(']');
        return toReturn.toString();
    }
}
