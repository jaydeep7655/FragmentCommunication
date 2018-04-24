package com.example.t186.fragmentcommunication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.t186.fragmentcommunication.adapter.ProductHeaderAdapter;
import com.example.t186.fragmentcommunication.sqlitedatabase.ProductDatabase;
import com.example.t186.fragmentcommunication.sqliteentity.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

public class ProductHeaderViewActivity extends AppCompatActivity {
    ProductHeaderViewActivity activity;
    ProductDatabase productDatabase;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView rvDetail;
    private StickyHeaderDecoration decor;
    ArrayList<Result> alResult;
    ProductHeaderAdapter productHeaderAdapter;
    //Create Map For header
    private Map<String, ArrayList<Result>> ProductMap;
    ArrayList<Result> alSupplierName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_header_view);
        activity = this;
        setupDatabase();
        mappingWidget();
        mappingData();
    }

    private void setupDatabase() {
        productDatabase = new ProductDatabase(activity);
    }

    private void mappingWidget() {
        ProductMap = new HashMap<>();
        alSupplierName = new ArrayList<>();


        Set<String> setSupplier = new HashSet<String>();
        alSupplierName = productDatabase.selectAllSupplierName();
        for (int i = 0; i < alSupplierName.size(); i++) {
            setSupplier.add(alSupplierName.get(i).getSupplierName());

        }
        ArrayList<Result> list = new ArrayList(setSupplier);


        //fromDatabase
        //alSupplierName = productDatabase.selectSupplierName();
        for (int i = 0; i < list.size(); i++) {
            ProductMap.put(String.valueOf(list.get(i)), productDatabase.selectDataBySupplierName(String.valueOf(list.get(i))));
        }
        rvDetail = (RecyclerView) findViewById(R.id.rvDetail);
    }

    private void mappingData() {
        alResult = productDatabase.selectData();
        setAdapter();
    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(activity);
        rvDetail.setLayoutManager(layoutManager);

        productHeaderAdapter = new ProductHeaderAdapter(activity, ProductMap);

        decor = new StickyHeaderDecoration(productHeaderAdapter);
        rvDetail.setAdapter(productHeaderAdapter);
        rvDetail.addItemDecoration(decor);

    }

}
