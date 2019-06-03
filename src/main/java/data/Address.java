package data;

/**
 *
 * @author marinaspari
 */
public class Address
{

    private String street;
    private int houseNr;
    private int zipCode;
    private String city;
    private String region;
    private String country;

    public Address(String street, int zipCode, String city, String country)
    {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        houseNr = 0;
    }

    public Address(String street, int houseNr, int zipCode, String city, String region, String country)
    {
        this.street = street;
        this.houseNr = houseNr;
        this.zipCode = zipCode;
        this.city = city;
        this.region = region;
        this.country = country;
    }

    public Address(String street, int houseNr, int zipCode, String city, String country)
    {
        this.street = street;
        this.houseNr = houseNr;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public int getHouseNr()
    {
        return houseNr;
    }

    public void setHouseNr(int houseNr)
    {
        this.houseNr = houseNr;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "Address{" + "street=" + street + ", houseNr=" + houseNr + ", zipCode=" + zipCode + ", city=" + city + ", region=" + region + ", country=" + country + '}';
    }

}
