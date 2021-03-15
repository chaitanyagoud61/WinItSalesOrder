package com.chaitanya.winitsalesorder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.chaitanya.winitsalesorder.databinding.BrandRowItemBinding;
import com.chaitanya.winitsalesorder.databinding.DataRowItemBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BrandItemAdapter extends RecyclerView.Adapter<BrandItemAdapter.BrandItemAdapterViewModel> implements Filterable {


    public Context context;
    public ArrayList<ItemModel> itemModelArrayList = new ArrayList<>();
    Added_qnty added_qnty;
    public Integer total_qnty = 0;
    HashMap<String, String> data_map = new HashMap();
    HashMap<String, String> search_data_map = new HashMap();
    HomeScreenFilter homeScreenFilter;

    public BrandItemAdapter(Context context, ArrayList<ItemModel> itemModels, Added_qnty added_qnty) {
        this.context = context;
        this.itemModelArrayList = itemModels;
        this.added_qnty = added_qnty;
    }

    @Override
    public BrandItemAdapter.BrandItemAdapterViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row_item, null);
        return new BrandItemAdapterViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BrandItemAdapterViewModel holder, int position) {
        final ItemModel itemModel = itemModelArrayList.get(position);
        holder.item_name.setText(itemModel.getItem_name().trim().length() > 0 ? itemModel.getItem_name() : "");
        holder.item_price.setText(itemModel.getItem_price().trim().length() > 0 ? itemModel.getItem_price() : "");
        if (search_data_map.containsKey(itemModel.getSku_code())) {

            holder.qnty.setText(search_data_map.get(itemModel.getSku_code()));
            holder.calculated_value.setText(String.valueOf(Integer.valueOf(itemModel.getItem_price()) * Integer.valueOf(holder.qnty.getText().toString())));
        }
        holder.qnty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (holder.qnty.hasFocus()) {
                    if (!holder.qnty.getText().toString().isEmpty() && !itemModel.getItem_price().isEmpty()) {
                        holder.calculated_value.setText(String.valueOf(Integer.valueOf(itemModel.getItem_price()) * Integer.valueOf(holder.qnty.getText().toString())));
                    } else if (holder.qnty.getText().toString().isEmpty() && !itemModel.getItem_price().isEmpty()) {
                        String val = "0";
                        holder.calculated_value.setText(String.valueOf(Integer.valueOf(itemModel.getItem_price()) * Integer.valueOf(val)));
                    }

                    if (Integer.valueOf(holder.calculated_value.getText().toString()) > 0 && data_map.containsKey(itemModel.getSku_code())) {

                        data_map.put(itemModel.getSku_code(), holder.calculated_value.getText().toString());
                        search_data_map.put(itemModel.getSku_code(), holder.qnty.getText().toString());
                        holder.add_food_item_selected(String.valueOf(data_map.size()), String.valueOf(getnetValue(data_map)));
                    } else if (Integer.valueOf(holder.calculated_value.getText().toString()) <= 0 && data_map.containsKey(itemModel.getSku_code())) {

                        data_map.remove(itemModel.getSku_code());
                        search_data_map.remove(itemModel.getSku_code());
                        holder.add_food_item_selected(String.valueOf(data_map.size()), String.valueOf(getnetValue(data_map)));
                    } else if (Integer.valueOf(holder.calculated_value.getText().toString()) > 0 && !data_map.containsKey(itemModel.getSku_code())) {
                        data_map.put(itemModel.getSku_code(), holder.calculated_value.getText().toString());
                        search_data_map.put(itemModel.getSku_code(), holder.qnty.getText().toString());
                        holder.add_food_item_selected(String.valueOf(data_map.size()), String.valueOf(getnetValue(data_map)));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public Integer getnetValue(HashMap<String, String> hashMap) {

        Set<String> keys = hashMap.keySet();
        Integer qnty = 0;
        for (String key : keys) {
            qnty = qnty + Integer.valueOf(hashMap.get(key));
        }

        return qnty;
    }


    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public Filter getFilter() {
        if (homeScreenFilter == null) {

            homeScreenFilter = new HomeScreenFilter(this, itemModelArrayList);
        }
        return homeScreenFilter;
    }

    public class BrandItemAdapterViewModel extends RecyclerView.ViewHolder {

        EditText qnty;
        TextView calculated_value, item_name, item_price;

        public BrandItemAdapterViewModel(View itemView) {
            super(itemView);
            qnty = itemView.findViewById(R.id.qnty);
            calculated_value = itemView.findViewById(R.id.cal_value);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
        }

        public void add_food_item_selected(final String total_lines, final String net_value) {
            added_qnty.added_qnty_rows_and_total_qnty(total_lines, net_value);

        }
    }
}
