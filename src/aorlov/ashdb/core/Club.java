package aorlov.ashdb.core;

public class Club {

    private int id;
    private String name;
    private String country;
    private String city;
    private String webSite;
    private String contactPerson;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String toString(){
        StringBuffer toReturn = new StringBuffer();
        toReturn.append("ID [");
        toReturn.append(id);
        toReturn.append(']');
        toReturn.append("name [");
        toReturn.append(name);
        toReturn.append(']');
        toReturn.append("country [");
        toReturn.append(country);
        toReturn.append(']');
        toReturn.append("city [");
        toReturn.append(city);
        toReturn.append(']');
        toReturn.append("Web-site [");
        toReturn.append(webSite);
        toReturn.append(']');
        return toReturn.toString();
    }

}