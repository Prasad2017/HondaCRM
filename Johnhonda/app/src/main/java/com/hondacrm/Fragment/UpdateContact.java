package com.hondacrm.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;


public class UpdateContact extends Fragment {

    View view;
    @BindViews({R.id.contactPerson, R.id.contact, R.id.emailId, R.id.companyAddress})
    List<TextInputEditText> textInputEditTexts;
    @BindViews({R.id.contactPersonLayout, R.id.contactLayout, R.id.emailIdLayout, R.id.companyAddressLayout})
    List<TextInputLayout> textInputLayouts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_contact, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Update Contact");

        try {

            Bundle bundle = getArguments();
            textInputEditTexts.get(0).setText("" + bundle.getString("name"));
            textInputEditTexts.get(1).setText("" + bundle.getString("phone"));
            textInputEditTexts.get(2).setText("" + bundle.getString("email"));
            textInputEditTexts.get(3).setText("" + bundle.getString("address"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {

        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }
}