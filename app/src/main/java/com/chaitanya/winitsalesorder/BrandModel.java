package com.chaitanya.winitsalesorder;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

public class BrandModel {

    private String brand_name="",brand_id="";
    OnBrandselected onBrandselected;

    public BrandModel(String brand_name, String brand_id,OnBrandselected onBrandselected) {
        this.brand_name = brand_name;
        this.brand_id = brand_id;
        this.onBrandselected = onBrandselected;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public void onclick(View view, BrandModel brandModel){
        onBrandselected.onbrandselected(brandModel);
    }

}
