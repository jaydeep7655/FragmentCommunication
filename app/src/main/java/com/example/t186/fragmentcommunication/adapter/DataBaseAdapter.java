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
import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by T186 on 5/1/2018.
 */

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.DatabaseViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<User> arResult;
    private OnItemClickListener onFirstNameClickListener;


    public interface OnItemClickListener {
        void onItemClick(@NonNull View itemView, int position, long id);
    }

    public DataBaseAdapter(@NonNull Context context, @NonNull ArrayList<User> arResult, @Nullable OnItemClickListener onFirstNameClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.arResult = arResult;
        Collections.sort(this.arResult, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return lhs.getFirstName().toUpperCase().compareTo(rhs.getFirstName().toUpperCase());
            }
        });


        this.onFirstNameClickListener = onFirstNameClickListener;
    }


    @Override
    public DatabaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DatabaseViewHolder(inflater.inflate(R.layout.item_display_database, parent, false));
    }

    @Override
    public void onBindViewHolder(DatabaseViewHolder holder, int position) {
        holder.bindView(position);

    }

    @Override
    public int getItemCount() {
        return arResult.size();
    }

    class DatabaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        TextView tvFirstname, tvLastname, tvAge, tvEmail;

        DatabaseViewHolder(View itemView) {
            super(itemView);
            tvFirstname = itemView.findViewById(R.id.tvFirstname);
            tvLastname = itemView.findViewById(R.id.tvLastname);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvAge = itemView.findViewById(R.id.tvAge);

            tvFirstname.setOnClickListener(v -> {
                if (onFirstNameClickListener != null) {
                    onFirstNameClickListener.onItemClick(v, position, position);
                }
            });
        }

        void bindView(int position) {
            this.position = position;

            User item = arResult.get(position);
            tvFirstname.setText(item.getFirstName());
            tvLastname.setText(item.getLastName());
            tvEmail.setText(item.getEmail());
            tvAge.setText(String.valueOf(item.getAge()));

        }

        @Override
        public void onClick(View v) {
            if (onFirstNameClickListener != null) {
                onFirstNameClickListener.onItemClick(v, position, position);
            }

        }
    }
}
