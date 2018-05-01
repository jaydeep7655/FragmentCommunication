package com.example.t186.fragmentcommunication.RoomDataBase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.t186.fragmentcommunication.MyApplication;
import com.example.t186.fragmentcommunication.R;
import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomdatabaseDisplayActivity extends AppCompatActivity {
    RoomdatabaseDisplayActivity activity;
    RecyclerView rvDataSearch;
    EditText etSearch;
    ArrayList<User> alUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdatabase_display);
        activity = this;
        findViews();
        getDatabase();
        mappingData();
    }

    private void findViews() {
        rvDataSearch = findViewById(R.id.rvDataSearch);
        etSearch = findViewById(R.id.etSearch);
    }

    private void mappingData() {
    }

    private void getDatabase() {
        MyApplication.appDatabase.userDao().liAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(liUser -> {
                    alUser = (ArrayList<User>) liUser;

                });
    }
}
