package com.example.listycity;

import java.io.Serializable;

public class City implements Serializable, Comparable<City> {
    private String name;
    private String province;

    public City() { }

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() { return name; }
    public String getProvince() { return province; }

    // Setters allow editing later if you add that feature
    public void setName(String name) { this.name = name; }
    public void setProvince(String province) { this.province = province; }

    @Override
    public String toString() {
        return name + ", " + province;
    }

    @Override
    public int compareTo(City city) {
        return this.name.compareTo(city.getName());
    }
}


