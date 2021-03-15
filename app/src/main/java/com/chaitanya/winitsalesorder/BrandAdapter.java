package com.chaitanya.winitsalesorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaitanya.winitsalesorder.databinding.BrandRowItemBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewModel> {


    public Context context;
    public ArrayList<BrandModel> brandModelArrayList = new ArrayList<>();
    public BrandAdapter(Context context,ArrayList<BrandModel> brandModelArrayList1) {
        this.context = context;
        this.brandModelArrayList = brandModelArrayList1;
    }

    @Override
    public BrandAdapter.BrandViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BrandRowItemBinding brandRowItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.brand_row_item, parent, false);
        return new BrandViewModel(brandRowItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewModel holder, int position) {
        BrandModel brandModel = brandModelArrayList.get(position);
        holder.brandRowItemBindingholderview.setBrandmodel(brandModel);
        holder.brandRowItemBindingholderview.masterLayt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.findViewById(R.id.master_layt).setBackgroundResource(R.drawable.onselected_brand);
            }
        });
        holder.bindData(brandModel);

    }

    @Override
    public int getItemCount() {
        return brandModelArrayList.size();
    }

    public class BrandViewModel extends RecyclerView.ViewHolder {

        BrandRowItemBinding brandRowItemBindingholderview;

        public BrandViewModel(BrandRowItemBinding brandRowItemBinding) {
            super(brandRowItemBinding.getRoot());
            this.brandRowItemBindingholderview = brandRowItemBinding;
        }

        public void bindData(Object object) {
            brandRowItemBindingholderview.setVariable(BR.brandmodel, object);
            brandRowItemBindingholderview.executePendingBindings();
        }
    }
}
