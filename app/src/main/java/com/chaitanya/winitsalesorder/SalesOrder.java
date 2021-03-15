package com.chaitanya.winitsalesorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SearchView;

import com.chaitanya.winitsalesorder.databinding.ActivitySalesOrderBinding;

import java.util.ArrayList;

public class SalesOrder extends AppCompatActivity implements OnBrandselected,Added_qnty {

    ActivitySalesOrderBinding salesOrderBinding;
    DataBaseHelper dataBaseHelper;
    SalesOrderViewModel salesOrderViewModel;
    BrandModel brandModel;
    ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    BrandAdapter brandAdapter;
    ItemModel itemModel;
    ArrayList<ItemModel> itemModelArrayList = new ArrayList<>();
    BrandItemAdapter brandItemAdapter;
    OnBrandselected onBrandselected;
    Added_qnty added_qnty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salesOrderViewModel = ViewModelProviders.of(this).get(SalesOrderViewModel.class);
        salesOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_sales_order);
        salesOrderBinding.setLifecycleOwner(this);
        salesOrderBinding.setSalesorderviewmodel(salesOrderViewModel);

        onBrandselected = this;
        added_qnty = this;
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());
        dataBaseHelper.open();
        getbrands();

        salesOrderBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (brandItemAdapter != null) {

                    brandItemAdapter.getFilter().filter(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (brandItemAdapter != null) {

                    brandItemAdapter.getFilter().filter(s);
                }
                return false;
            }
        });
    }


    public void getbrands() {
        Cursor brands_cursor = null;
        try {
            brands_cursor = dataBaseHelper.getSku();
            while (brands_cursor != null && brands_cursor.moveToNext()) {

                brandModel = new BrandModel(
                        brands_cursor.getString(brands_cursor.getColumnIndex("Name")) != null && brands_cursor.getString(brands_cursor.getColumnIndex("Name")).trim().length() > 0 ?
                                brands_cursor.getString(brands_cursor.getColumnIndex("Name")) : "",
                        brands_cursor.getString(brands_cursor.getColumnIndex("SKUCode")) != null && brands_cursor.getString(brands_cursor.getColumnIndex("SKUCode")).trim().length() > 0 ?
                                brands_cursor.getString(brands_cursor.getColumnIndex("SKUCode")) : "", onBrandselected);
                brandModelArrayList.add(brandModel);
            }

            brandAdapter = new BrandAdapter(this, brandModelArrayList);
            salesOrderBinding.brandRecylrView.setAdapter(brandAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getItemsBybrand(final String sku_code) {

        itemModelArrayList = new ArrayList<>();
        Cursor brands_item_cursor = null;
        try {
            brands_item_cursor = dataBaseHelper.getSkuprice(sku_code);
            while (brands_item_cursor != null && brands_item_cursor.moveToNext()) {
                String price = brands_item_cursor.getString(brands_item_cursor.getColumnIndex("Pr")) != null && brands_item_cursor.getString(brands_item_cursor.getColumnIndex("Pr")).trim().length() > 0 ?
                         brands_item_cursor.getString(brands_item_cursor.getColumnIndex("Pr")): "0";
               String name = brands_item_cursor.getString(brands_item_cursor.getColumnIndex("itemName")) != null && brands_item_cursor.getString(brands_item_cursor.getColumnIndex("itemName")).trim().length() > 0 ?
                        brands_item_cursor.getString(brands_item_cursor.getColumnIndex("itemName")): "";
               String code =  brands_item_cursor.getString(brands_item_cursor.getColumnIndex("code")) != null && brands_item_cursor.getString(brands_item_cursor.getColumnIndex("code")).trim().length() > 0 ?
                       brands_item_cursor.getString(brands_item_cursor.getColumnIndex("code")): "";

               String[] value = price.split("\\.");
                itemModel = new ItemModel(value[0],name,code);

                itemModelArrayList.add(itemModel);
            }

            brandItemAdapter = new BrandItemAdapter(this, itemModelArrayList,added_qnty);
            salesOrderBinding.brandItemsDataRcylr.setItemViewCacheSize(itemModelArrayList.size());
            salesOrderBinding.brandItemsDataRcylr.setAdapter(brandItemAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onbrandselected(BrandModel brandModel1) {

        salesOrderBinding.onempty.setVisibility(View.GONE);
        salesOrderBinding.headerRecylr.setVisibility(View.VISIBLE);
        salesOrderBinding.totalLines.setText("0");
        salesOrderBinding.netValue.setText("0");
        getItemsBybrand(brandModel1.getBrand_id());
    }

    @Override
    public void added_qnty_rows_and_total_qnty(String total_lines, String net_qnty) {

        salesOrderBinding.totalLines.setText(total_lines);
        salesOrderBinding.netValue.setText(net_qnty);
    }
}
