package com.hondacrm.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.hondacrm.Adapter.EnquiryAdapter;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.EnquiryResponse;
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


public class EnquiryList extends Fragment {

    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noDataFound)
    TextView noDataFound;
    @BindView(R.id.searchEnquiry)
    TextInputEditText searchEnquiry;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    int page = 1, maxPage;
    List<EnquiryResponse> enquiryResponseList = new ArrayList<>();
    List<EnquiryResponse> searchEnquiryResponseList = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_enquiry_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Enquiry");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .permitNetwork()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }

        searchEnquiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchEnquiryData(s.toString());
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
                    getEnquiryList();
                }
            }
        });

        return view;
    }

    private void searchEnquiryData(String s) {

        searchEnquiryResponseList = new ArrayList<>();
        searchEnquiryResponseList.clear();

        if (s.length() > 0) {

            for (int i = 0; i < enquiryResponseList.size(); i++)
                if ((enquiryResponseList.get(i).getName() + "" + enquiryResponseList.get(i).getPhone()).toLowerCase().contains(s.toLowerCase().trim())) {
                    searchEnquiryResponseList.add(enquiryResponseList.get(i));
                }

            if (searchEnquiryResponseList.size() < 1) {
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
            } else {
                noDataFound.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {

            searchEnquiryResponseList = new ArrayList<>();
            for (int i = 0; i < enquiryResponseList.size(); i++) {
                searchEnquiryResponseList.add(enquiryResponseList.get(i));
            }

            recyclerView.setVisibility(View.VISIBLE);
            noDataFound.setVisibility(View.GONE);

        }

        EnquiryAdapter adapter = new EnquiryAdapter(getActivity(), searchEnquiryResponseList, categoryResponseList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(searchEnquiryResponseList.size() - 1);
        recyclerView.setHasFixedSize(true);

    }

    @OnClick({R.id.addEnquiry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addEnquiry:
                ((MainPage) getActivity()).loadFragment(new AddEnquiry(), true);
                break;
        }
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getCloserList();
            getEnquiryList();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getEnquiryList() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        searchEnquiry.setVisibility(View.GONE);
        noDataFound.setVisibility(View.GONE);

        Call<List<EnquiryResponse>> call;
        if (Integer.parseInt(MainPage.userId) == 1) {
            call = Api.getClient().getAdminEnquiryPaginationList(MainPage.userId, page, 10);
        } else {
            call = Api.getClient().getEnquiryPaginationList(MainPage.userId, page, 10);
        }

        call.enqueue(new Callback<List<EnquiryResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryResponse>> call, Response<List<EnquiryResponse>> response) {

                if (response.isSuccessful()) {

                    enquiryResponseList.addAll(response.body());
                    if (enquiryResponseList.size() > 0) {

                        // get headers
                        String headers = response.headers().get("Paging-Headers");
                        try {
                            JSONObject jsonObject = new JSONObject(headers);
                            maxPage = Integer.parseInt(jsonObject.getString("totalPages"));
                            Log.e("totalPages", "" + maxPage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        EnquiryAdapter adapter = new EnquiryAdapter(getActivity(), enquiryResponseList, categoryResponseList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(enquiryResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        progressBar.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        searchEnquiry.setVisibility(View.VISIBLE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        searchEnquiry.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                    searchEnquiry.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<EnquiryResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.GONE);
                searchEnquiry.setVisibility(View.GONE);
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

    public void getCloserList() {

        Call<List<CategoryResponse>> call = Api.getClient().getCloserList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    categoryResponseList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

            }
        });

    }

}