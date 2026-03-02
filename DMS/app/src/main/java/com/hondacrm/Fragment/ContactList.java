package com.hondacrm.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Adapter.ContactAdapter;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.ContactResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactList extends Fragment {

    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noDataFound)
    TextView noDataFound;
    @BindView(R.id.searchContact)
    TextInputEditText textInputEditText;
    List<ContactResponse> contactResponseList = new ArrayList<>();
    List<ContactResponse> searchContactResponseList = new ArrayList<>();
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    int page = 1, maxPage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Contact");


        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchContact(s.toString());
            }
        });

        MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new Home(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("tag", "onKey Back listener is working!!!");
                    try {
                        ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                        ((MainPage) getActivity()).loadFragment(new Home(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //check condition
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                //Increase page size
                page++;
                if (page <= maxPage) {
                    getContactList();
                }
            }
        });

        return view;
    }

    private void searchContact(String s) {

        searchContactResponseList = new ArrayList<>();
        searchContactResponseList.clear();

        if (s.length() > 0) {

            for (int i = 0; i < contactResponseList.size(); i++)
                if ((contactResponseList.get(i).getName() + "" + contactResponseList.get(i).getPhone()).toLowerCase().contains(s.toLowerCase().trim())) {
                    searchContactResponseList.add(contactResponseList.get(i));
                }

            if (searchContactResponseList.size() < 1) {
                recyclerView.setVisibility(View.GONE);

            } else {
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {

            searchContactResponseList = new ArrayList<>();
            for (int i = 0; i < contactResponseList.size(); i++) {
                searchContactResponseList.add(contactResponseList.get(i));
            }

            recyclerView.setVisibility(View.VISIBLE);

        }

        ContactAdapter adapter = new ContactAdapter(getActivity(), searchContactResponseList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(searchContactResponseList.size() - 1);
        recyclerView.setHasFixedSize(true);

    }

    @OnClick({R.id.addContact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addContact:
                ((MainPage) getActivity()).loadFragment(new AddContact(), true);
                break;
        }
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getContactList();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getContactList() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noDataFound.setVisibility(View.GONE);
        textInputEditText.setVisibility(View.GONE);

        Call<List<ContactResponse>> call;
        if (Integer.parseInt(MainPage.userId) == 1) {
            call = Api.getClient().getAdminContactPaginationList(page, 10);
        } else {
            call = Api.getClient().getContactPaginationList(MainPage.userId, page, 10);
        }
        call.enqueue(new Callback<List<ContactResponse>>() {
            @Override
            public void onResponse(Call<List<ContactResponse>> call, Response<List<ContactResponse>> response) {

                if (response.isSuccessful()) {

                    contactResponseList = response.body();
                    if (contactResponseList.size() > 0) {

                        // get headers
                        String headers = response.headers().get("Paging-Headers");
                        try {
                            JSONObject jsonObject = new JSONObject(headers);
                            maxPage = Integer.parseInt(jsonObject.getString("totalPages"));
                            Log.e("totalPages", "" + maxPage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        ContactAdapter adapter = new ContactAdapter(getActivity(), contactResponseList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(contactResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        progressBar.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        textInputEditText.setVisibility(View.VISIBLE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.VISIBLE);
                        textInputEditText.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                    textInputEditText.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ContactResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.GONE);
                textInputEditText.setVisibility(View.GONE);
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

}