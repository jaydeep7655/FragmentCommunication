package com.example.t186.fragmentcommunication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.t186.fragmentcommunication.FirstFragmentListener;
import com.example.t186.fragmentcommunication.R;

public class FragmentAdd extends Fragment {
    EditText editText;
    Button buttonSendMessage;
    private FirstFragmentListener firstFragmentListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FirstFragmentListener) {
            firstFragmentListener = (FirstFragmentListener) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_add, container, false);
        mappingWidget(view);
        return view;


    }

    private void mappingWidget(View view) {
        editText = view.findViewById(R.id.editText);
        buttonSendMessage = view.findViewById(R.id.buttonSendMessage);
        buttonSendMessage.setOnClickListener(v -> {
            if(firstFragmentListener != null){
                firstFragmentListener.getMessage(editText.getText().toString());
            }

        });
    }


}
