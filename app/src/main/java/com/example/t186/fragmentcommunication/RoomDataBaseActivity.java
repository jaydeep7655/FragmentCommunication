package com.example.t186.fragmentcommunication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;
import com.example.t186.fragmentcommunication.utility.UDF;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomDataBaseActivity extends AppCompatActivity implements View.OnClickListener {

    RoomDataBaseActivity activity;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAge;
    private EditText etEmail;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        activity = this;
        findViews();
    }

    private void findViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
    }

    /**
     * validation on password
     *
     * @return boolean
     */
    private boolean validate() {
        if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
            etFirstName.setError(getString(R.string.error_field_required));
            etFirstName.requestFocus();
            return false;
        } else if (!etFirstName.getText().toString().trim().equalsIgnoreCase("") && etFirstName.getText().toString().trim().length() < 3) {
            etFirstName.setError(getString(R.string.minimum_3));
            etFirstName.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(etLastName.getText().toString().trim())) {
            etLastName.setError(getString(R.string.error_field_required));
            etLastName.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(etAge.getText().toString().trim())) {
            etAge.setError(getString(R.string.error_field_required));
            etAge.requestFocus();
            return false;


        } else if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.error_field_required));
            etEmail.requestFocus();
            return false;
        } else if (!UDF.emailMatches(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            if (validate()) {
                UDF.hideKeyboard(activity);
                User user = new User();
                user.setFirstName(etFirstName.getText().toString());
                user.setLastName(etLastName.getText().toString());
                user.setAge(Integer.parseInt(etAge.getText().toString()));
                user.setEmail(etEmail.getText().toString());

                Single.fromCallable(() -> MyApplication.appDatabase.userDao().insertUser(user))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {
                            initilizeEditText();
                            Toast.makeText(activity, "inserted Count  ::" + aLong, Toast.LENGTH_SHORT).show();
                        });
            }
        }

    }

    private void initilizeEditText() {
        etFirstName.setText("");
        etLastName.setText("");
        etAge.setText("");
        etEmail.setText("");
        etFirstName.requestFocus();
    }
}
