package com.example.t186.fragmentcommunication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t186.fragmentcommunication.ProductHeaderViewActivity;
import com.example.t186.fragmentcommunication.R;
import com.example.t186.fragmentcommunication.sqliteentity.Result;

import java.util.ArrayList;
import java.util.Map;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by T186 on 4/23/2018.
 */

public class ProductHeaderAdapter extends RecyclerView.Adapter<ProductHeaderAdapter.ProductListingHolder> implements
        StickyHeaderAdapter<ProductHeaderAdapter.HeaderHolder> {
    Activity activity;
    private Map<String, ArrayList<Result>> ProductMap;
    private LayoutInflater mInflater;
    ArrayList<String> alHeader = new ArrayList<>();

    public ProductHeaderAdapter(ProductHeaderViewActivity activity, Map<String, ArrayList<Result>> ProductMap) {
        this.activity = activity;
        this.ProductMap = ProductMap;
        alHeader = new ArrayList<>(ProductMap.keySet());
        // setData(alResult, false);
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ProductHeaderAdapter.ProductListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater
                .inflate(R.layout.item_product_layout, parent, false);
        return new ProductListingHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductListingHolder holder, int position) {
        Result result = getResult(position);
        holder.tvFirstname.setText(result.getFirstname());
        holder.tvLastname.setText(result.getLastname());
        holder.tvEmail.setText(result.getEmail());
        holder.tvCity.setText(result.getCity());
        holder.tvSuppliername.setText(result.getSupplierName());
        holder.tvSku.setText(result.getSku());
    }


    private Result getResult(int position) {
        Result result = null;
        for (String header : alHeader) {
            ArrayList<Result> productList = ProductMap.get(header);
            if (position >= productList.size()) {
                position = position - productList.size();
            } else {
                result = productList.get(position);
                break;
            }
        }
        return result;
    }


    @Override
    public int getItemCount() {
        int size = 0;
        for (String key : alHeader) {
            size = size + ProductMap.get(key).size();
        }

        return size;
    }

    @Override
    public long getHeaderId(int position) {
        String header;
        for (int i = 0; i < alHeader.size(); i++) {
            header = alHeader.get(i);
            ArrayList<Result> attractionList = ProductMap.get(header);
            if (position >= attractionList.size()) {
                position = position - attractionList.size();
            } else {
                return i;
            }
        }
        return position;
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_test, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        String headerTitle = "No Header";
        for (String header : alHeader) {
            ArrayList<Result> attractionList = ProductMap.get(header);
            if (position >= attractionList.size()) {
                position = position - attractionList.size();
            } else {
                headerTitle = header;
                break;
            }
        }
        viewholder.header.setText(headerTitle.toUpperCase());
    }

    class ProductListingHolder extends RecyclerView.ViewHolder {
        TextView tvFirstname, tvLastname, tvSku, tvEmail, tvSuppliername, tvCity;

        ProductListingHolder(View itemView) {
            super(itemView);
            tvFirstname = itemView.findViewById(R.id.tvFirstname);
            tvLastname = itemView.findViewById(R.id.tvLastname);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvSuppliername = itemView.findViewById(R.id.tvSuppliername);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView header;

        HeaderHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.tvHeader);

        }
    }
}
