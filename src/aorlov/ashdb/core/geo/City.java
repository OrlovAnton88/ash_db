package aorlov.ashdb.core.geo;


public class City {

    private int cityId;
    private int countryId;
    private int regionId;
    private String name;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String toString(){
        StringBuffer toReturn = new StringBuffer("City [id: ");
        toReturn.append(cityId);
        toReturn.append(" name: ");
        toReturn.append(name);
        toReturn.append(" regionId: ");
        toReturn.append(regionId);
        toReturn.append(" countryid: ");
        toReturn.append(countryId);
        toReturn.append(']');
        return toReturn.toString();
    }

}
