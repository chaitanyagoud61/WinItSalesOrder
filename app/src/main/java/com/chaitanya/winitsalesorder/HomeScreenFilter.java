package com.chaitanya.winitsalesorder;

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenFilter extends Filter {

    public Context context;
    public BrandItemAdapter adapter;
    public List<ItemModel> model_list ;

    public HomeScreenFilter(BrandItemAdapter adapter, List<ItemModel> model_list) {
        this.context = context;
        this.adapter = adapter;
        this.model_list = model_list;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults village_filter_results = new FilterResults();
        if(model_list!=null && model_list.size()>0 && charSequence!=null && charSequence.toString().trim().length()>0){
            List<ItemModel> filter_list = new ArrayList<>();

            for (ItemModel single_model_object : model_list) {
                if(single_model_object.getItem_name().toLowerCase().contains(charSequence.toString().toLowerCase())){

                    filter_list.add(single_model_object);
                }
            }
            village_filter_results.count = filter_list.size();
            village_filter_results.values = filter_list;
            return village_filter_results;
        }

        village_filter_results.count = model_list.size();
        village_filter_results.values = model_list;
        return village_filter_results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.itemModelArrayList = (ArrayList<ItemModel>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
