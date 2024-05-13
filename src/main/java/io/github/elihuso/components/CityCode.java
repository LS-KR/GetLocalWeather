package io.github.elihuso.components;

public class CityCode {
    private final String city;
    private final String code;

    public CityCode(String city, String code) {
        this.city = city;
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }
}
