package com.example.t186.fragmentcommunication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t186.fragmentcommunication.R;
import com.example.t186.fragmentcommunication.sqliteentity.Result;

import java.util.ArrayList;

/**
 * Created by T186 on 4/23/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private LayoutInflater inflater;
    ArrayList<Result> arResult;
    private OnItemClickListener onItemClickListener;
    //private OnItemClickListener onFirstNameClickListener;
    // private OnItemClickListener  onLastNameClickListener;


    public interface OnItemClickListener {
        void onItemClick(@NonNull View itemView, int position, long id);
    }

    public ProductAdapter(@NonNull Context context,
                          @NonNull ArrayList<Result> arResult,
                          // @Nullable OnItemClickListener onFirstNameClickListener
                          // @Nullable OnItemClickListener onLastNameClickListener,
                          @Nullable OnItemClickListener onItemClickListener
    ) {
        this.inflater = LayoutInflater.from(context);
        this.arResult = arResult;
        //this.onFirstNameClickListener = onFirstNameClickListener;
        //   this.onLastNameClickListener = onLastNameClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(inflater.inflate(R.layout.custom_product_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return arResult.size();
    }


     class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        TextView tvFirstname, tvLastname, tvSku, tvEmail, tvSuppliername, tvCity;

        ProductViewHolder(View itemView) {
            super(itemView);
            tvFirstname = (TextView) itemView.findViewById(R.id.tvFirstname);
            tvLastname = (TextView) itemView.findViewById(R.id.tvLastname);
            tvSku = (TextView) itemView.findViewById(R.id.tvSku);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvSuppliername = (TextView) itemView.findViewById(R.id.tvSuppliername);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);


            /*tvFirstname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFirstNameClickListener != null) {
                        onFirstNameClickListener.onItemClick(v, position, position);
                    }
                }
            });*/
       /*     tvLastname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLastNameClickListener != null) {
                        onLastNameClickListener.onItemClick(v, position, position);
                    }
                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, position, position);
                    }

                }
            });
        }

        void bindView(int position) {
            this.position = position;

            Result item = arResult.get(position);
            tvFirstname.setText(item.getFirstname());
            tvLastname.setText(item.getLastname());
            tvEmail.setText(item.getEmail());
            tvCity.setText(item.getCity());
            tvSuppliername.setText(item.getSupplierName());
            tvSku.setText(item.getSku());

        }

        @Override
        public void onClick(View v) {
            if (
                //onFirstNameClickListener != null
                //  && onLastNameClickListener != null &&
                    onItemClickListener != null) {
                //  onFirstNameClickListener.onItemClick(v, position, position);
                //onLastNameClickListener.onItemClick(v, position, position);
                onItemClickListener.onItemClick(v, position, position);
            }
        }
    }
}
