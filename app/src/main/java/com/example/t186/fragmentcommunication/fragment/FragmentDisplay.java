package com.example.t186.fragmentcommunication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t186.fragmentcommunication.R;


public class FragmentDisplay extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_display, container, false);
        mappingWidget(view);
        return view;
    }

    private void mappingWidget(View view) {
        textView = view.findViewById(R.id.textView);
    }



    public TextView getTextView(){
        return textView;
    }
}
