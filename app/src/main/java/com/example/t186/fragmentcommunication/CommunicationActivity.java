package com.example.t186.fragmentcommunication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.t186.fragmentcommunication.fragment.FragmentAdd;
import com.example.t186.fragmentcommunication.fragment.FragmentDisplay;

public class CommunicationActivity extends AppCompatActivity implements FirstFragmentListener {
    CommunicationActivity activity;
    private FragmentAdd firstFragment;
    private FragmentDisplay secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        activity = this;
        mappingWidget(savedInstanceState);
        maapingData();

    }

    private void maapingData() {

    }

    private void mappingWidget(Bundle savedInstanceState) {
        firstFragment = new FragmentAdd();
        secondFragment = new FragmentDisplay();
        if (savedInstanceState == null) {
            addFragment(firstFragment, R.id.frameLayout1);
            addFragment(secondFragment, R.id.frameLayout2);
        }


    }

    private void addFragment(Fragment fragment, int layoutId) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment).commit();
    }


    @Override
    public void getMessage(String message) {
        secondFragment.getTextView().setText(message);

    }
}
