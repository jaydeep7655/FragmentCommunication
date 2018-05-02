package com.example.t186.fragmentcommunication.RoomDataBase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.t186.fragmentcommunication.MyApplication;
import com.example.t186.fragmentcommunication.R;
import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;
import com.example.t186.fragmentcommunication.adapter.DataBaseAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RoomdatabaseDisplayActivity extends AppCompatActivity {
    RoomdatabaseDisplayActivity activity;
    RecyclerView rvDataSearch;
    EditText etSearch;


    //RxJava
    private static final int MIN_LENGTH = 2;
    private Disposable searchSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdatabase_display);
        activity = this;
        findViews();
        init();
        getDatabase();
        mappingData();
    }

    private void findViews() {
        rvDataSearch = findViewById(R.id.rvDataSearch);
        etSearch = findViewById(R.id.etSearch);
    }

    private void mappingData() {
    }

    private void init() {
        rvDataSearch.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDatabase() {
        MyApplication.appDatabase.userDao().liAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(liUser -> {
                    if (liUser.size() > 0) {
                        setAdapter(liUser);
                    }

                });
    }

    private void setAdapter(List<User> liUser) {
        rvDataSearch.setAdapter(new DataBaseAdapter(activity, (ArrayList<User>) liUser, (itemView, position, id) -> {

        }));
    }
}
