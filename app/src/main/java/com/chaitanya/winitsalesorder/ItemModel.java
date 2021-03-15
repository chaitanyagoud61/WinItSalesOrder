package com.chaitanya.winitsalesorder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ItemModel {


    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    private String item_price="";
    private String item_name="";

    public ItemModel(String item_price, String item_name, String sku_code) {
        this.item_price = item_price;
        this.item_name = item_name;
        this.sku_code = sku_code;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    private String sku_code="";

    public void onclickitem(View view, final ItemModel itemModel){


    }

}
