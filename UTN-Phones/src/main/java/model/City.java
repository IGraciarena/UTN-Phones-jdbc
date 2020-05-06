package model;

public class City {

    private Integer cityId;
    private String name;
    private Province province;

    public City(Integer cityId, String name, Province province) {
        this.cityId = cityId;
        this.name = name;
        this.province = province;
    }

    public City(String name, Province province) {
        this.name = name;
        this.province = province;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return this.cityId != ((City) o).getCityId();

    }

    @Override
    public int hashCode() {
        return cityId.hashCode();
    }
}
