package com.example.t186.fragmentcommunication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.t186.fragmentcommunication.adapter.ProductAdapter;
import com.example.t186.fragmentcommunication.sqlitedatabase.ProductDatabase;
import com.example.t186.fragmentcommunication.sqliteentity.Apicall;
import com.example.t186.fragmentcommunication.sqliteentity.Result;
import com.example.t186.fragmentcommunication.utility.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.example.t186.fragmentcommunication.rest.ApiClient.getApiInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MainActivity activity;
    CompositeDisposable mCompositeDisposable;
    ProgressDialog progressDialog;
    RecyclerView rvProduct;
    Button btnSave;
    ArrayList<Result> arResult;
    ProductDatabase productDatabase;
    Set<String> setSupplier;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        mappingWidget();
        init();
        mappingData();
    }

    private void mappingWidget() {
        productDatabase = new ProductDatabase(activity);
        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    private void init() {
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
    }

    private void mappingData() {
        mCompositeDisposable = new CompositeDisposable();
        progressDialog = new ProgressDialog(activity);


        mCompositeDisposable.add(getApiInterface(activity)
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> ProgressDialogUtils.showProgressDialog(activity, progressDialog))
                .doOnComplete(() -> ProgressDialogUtils.dismissProgressDialog(activity, progressDialog))
                .subscribe(this::handleResponse, this::handleError));

    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(Response<Apicall> apicallResponse) {
        arResult = (ArrayList<Result>) apicallResponse.body().getResult();
        if (arResult.size() > 0) {
            setAdapter();

        }
    }

    private void setAdapter() {
        rvProduct.setAdapter(new ProductAdapter(this, arResult,
                (ProductAdapter.OnItemClickListener) /*(itemView, position, id) -> {
                    Toast.makeText(activity,"FirstName Position"+ position,Toast.LENGTH_SHORT).show();
                }, (itemView, position, id) -> {
            Log.d("TAG", "LastName: Last" + position);

        },*/ (itemView, position, id) -> {
                    if (position == 0) {
                        Intent i = new Intent(activity, CommunicationActivity.class);
                        startActivity(i);
                    } else if (position == 1) {

                    }
                }));

    }

    public void onClick(View v) {
        if (v == btnSave) {
            setSupplier = new HashSet<String>();
            if (arResult != null && arResult.size() > 0) {
                productDatabase.deleteAllTable();

                for (int i = 0; i < arResult.size(); i++) {
                    long result = productDatabase.addProduct(
                            arResult.get(i).getFirstname(),
                            arResult.get(i).getLastname(),
                            arResult.get(i).getSku(),
                            arResult.get(i).getEmail(),
                            arResult.get(i).getSupplierName(),
                            arResult.get(i).getCity());
                    setSupplier.add(arResult.get(i).getSupplierName());

                }
                Iterator iterator = setSupplier.iterator();
                while (iterator.hasNext()) {
                    long result = productDatabase.addSuppliers((String) iterator.next());
                }
            }
            Intent i = new Intent(activity, ProductHeaderViewActivity.class);
            startActivity(i);
        }

    }
}
