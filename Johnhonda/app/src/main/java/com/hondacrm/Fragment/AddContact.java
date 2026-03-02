package com.hondacrm.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddContact extends Fragment {

    View view;
    @BindViews({R.id.contactPerson, R.id.contact, R.id.emailId, R.id.companyAddress})
    List<TextInputEditText> textInputEditTexts;
    @BindViews({R.id.contactPersonLayout, R.id.contactLayout, R.id.emailIdLayout, R.id.companyAddressLayout})
    List<TextInputLayout> textInputLayouts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Create Contact");


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .permitNetwork()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }


        return view;

    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:

                if (textInputEditTexts.get(0).getText().toString().length() > 0) {
                    if (textInputEditTexts.get(1).getText().toString().length() > 0) {
                        if (textInputEditTexts.get(2).getText().toString().length() > 0) {
                            addContact();
                        } else {
                            Toast.makeText(getActivity(), "Enter Email Id", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Enter Contact Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Enter Contact Person", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void addContact() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<String> call = Api.getClient().addContact(textInputEditTexts.get(0).getText().toString(), textInputEditTexts.get(1).getText().toString(), textInputEditTexts.get(2).getText().toString(), textInputEditTexts.get(3).getText().toString(), MainPage.userId, MainPage.userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    Toasty.success(getActivity(), "Contact Saved Successfully", Toasty.LENGTH_SHORT, true).show();
                    try {
                        ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                        ((MainPage) getActivity()).loadFragment(new ContactList(), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    pDialog.dismiss();
                    Toasty.error(getActivity(), "Contact fail to save", Toasty.LENGTH_SHORT, true).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pDialog.dismiss();
                Log.e("serverError", "" + t.getMessage());
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.error_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextView txtyes = dialog.findViewById(R.id.yes);

                txtyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

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