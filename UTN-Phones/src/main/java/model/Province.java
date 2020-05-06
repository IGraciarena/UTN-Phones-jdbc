package model;

public class Province {


    private Integer provinceId;
    private String province;


    public Province(Integer provinceId, String province) {
        this.provinceId = provinceId;
        this.province = province;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


}
