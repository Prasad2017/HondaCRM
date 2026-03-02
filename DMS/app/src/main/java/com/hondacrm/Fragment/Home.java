package com.hondacrm.Fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hondacrm.Activity.MainPage;
import com.hondacrm.Adapter.EnquiryAdapter;
import com.hondacrm.Adapter.QuotationAdapter;
import com.hondacrm.Adapter.TodayFollowUpAdapter;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.CountResponse;
import com.hondacrm.Module.EnquiryResponse;
import com.hondacrm.Module.QuotationResponse;
import com.hondacrm.Module.TodayFollowupResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {

    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindViews({R.id.bookedEnquiryCount, R.id.lostEnquiryCount, R.id.todayEnquiry, R.id.todayFollowup, R.id.todayQuotationFollowup, R.id.todayQuotation,
            R.id.openEnquiryCount, R.id.closeEnquiryCount, R.id.activityOpenEnquiryCount, R.id.activityTotalEnquiryCount, R.id.activityCloseEnquiryCount})
    List<TextView> textViews;
    @BindViews({R.id.enquiry, R.id.activity, R.id.noDataFound})
    List<TextView> textView;
    @BindViews({R.id.enquiryLayout, R.id.activityLayout, R.id.countLayout, R.id.activityCountLayout, R.id.followupLayout})
    List<LinearLayout> linearLayouts;
    List<EnquiryResponse> todayEnquiryResponseList = new ArrayList<>();
    List<QuotationResponse> todayQuotationResponseList = new ArrayList<>();
    List<TodayFollowupResponse> todayFollowupResponseList = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        MainPage.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainPage) getActivity()).loadFragment(new FilterVehicle(), true);
            }
        });

        return view;

    }

    @OnClick({R.id.enquiry, R.id.activity, R.id.addEnquiry, R.id.todayEnquiry, R.id.todayFollowup, R.id.todayQuotationFollowup, R.id.todayQuotation})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.enquiry:

                textView.get(0).setBackground(getActivity().getDrawable(R.drawable.enq_shape_et_border));
                textView.get(0).setTextColor(getActivity().getResources().getColor(R.color.white));

                textView.get(1).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textView.get(1).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                textViews.get(2).setBackground(getActivity().getDrawable(R.drawable.enq_shape_et_border));
                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.white));

                textViews.get(5).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textViews.get(5).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                linearLayouts.get(1).setVisibility(View.VISIBLE);
                linearLayouts.get(2).setVisibility(View.VISIBLE);
                linearLayouts.get(3).setVisibility(View.GONE);
                linearLayouts.get(4).setVisibility(View.GONE);

                getTodayEnquiry();

                break;

            case R.id.activity:

                textView.get(1).setBackground(getActivity().getDrawable(R.drawable.activity_shape_et_border));
                textView.get(1).setTextColor(getActivity().getResources().getColor(R.color.white));

                textView.get(0).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textView.get(0).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                linearLayouts.get(1).setVisibility(View.GONE);
                linearLayouts.get(2).setVisibility(View.GONE);
                linearLayouts.get(3).setVisibility(View.VISIBLE);
                linearLayouts.get(4).setVisibility(View.VISIBLE);

                getTodayEnquiryFollowup();

                break;

            case R.id.todayEnquiry:

                textViews.get(2).setBackground(getActivity().getDrawable(R.drawable.enq_shape_et_border));
                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.white));

                textViews.get(5).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textViews.get(5).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));


                getTodayEnquiry();

                break;

            case R.id.todayFollowup:

                textViews.get(3).setBackground(getActivity().getDrawable(R.drawable.enq_shape_et_border));
                textViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.white));

                textViews.get(4).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textViews.get(4).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                getTodayEnquiryFollowup();

                break;

            case R.id.todayQuotationFollowup:

                textViews.get(4).setBackground(getActivity().getDrawable(R.drawable.enq_shape_et_border));
                textViews.get(4).setTextColor(getActivity().getResources().getColor(R.color.white));

                textViews.get(3).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                getTodayQuotationFollowup();

                break;

            case R.id.todayQuotation:

                textViews.get(5).setBackground(getActivity().getDrawable(R.drawable.enq_shape_et_border));
                textViews.get(5).setTextColor(getActivity().getResources().getColor(R.color.white));

                textViews.get(2).setBackground(getActivity().getDrawable(R.drawable.shape_et_border));
                textViews.get(2).setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));


                getTodayQuotation();

                break;

            case R.id.addEnquiry:
                ((MainPage) getActivity()).loadFragment(new AddEnquiry(), true);
                break;
        }
    }

    private void getTodayQuotation() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<List<QuotationResponse>> call = Api.getClient().getTodayQuotation("" + MainPage.userId);
        call.enqueue(new Callback<List<QuotationResponse>>() {
            @Override
            public void onResponse(Call<List<QuotationResponse>> call, Response<List<QuotationResponse>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    todayQuotationResponseList = response.body();
                    if (todayQuotationResponseList.size() > 0) {

                        QuotationAdapter adapter = new QuotationAdapter(getActivity(), todayQuotationResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        adapter.notifyDataSetChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                        textView.get(2).setVisibility(View.GONE);

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textView.get(2).setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textView.get(2).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<QuotationResponse>> call, Throwable t) {
                Log.e("todayEnquiryError", "" + t.getMessage());
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textView.get(2).setVisibility(View.VISIBLE);
            }
        });


    }

    private void getTodayQuotationFollowup() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<List<TodayFollowupResponse>> call = Api.getClient().getTodayQuotationFollowup("" + MainPage.userId);
        call.enqueue(new Callback<List<TodayFollowupResponse>>() {
            @Override
            public void onResponse(Call<List<TodayFollowupResponse>> call, Response<List<TodayFollowupResponse>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    todayFollowupResponseList = response.body();
                    if (todayFollowupResponseList.size() > 0) {

                        TodayFollowUpAdapter followUpAdapter = new TodayFollowUpAdapter(getActivity(), todayFollowupResponseList, "quotation");
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(followUpAdapter);
                        followUpAdapter.notifyDataSetChanged();
                        followUpAdapter.notifyItemInserted(todayFollowupResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        recyclerView.setVisibility(View.VISIBLE);
                        textView.get(2).setVisibility(View.GONE);

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textView.get(2).setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textView.get(2).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TodayFollowupResponse>> call, Throwable t) {
                Log.e("todayFollowupError", "" + t.getMessage());
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textView.get(2).setVisibility(View.VISIBLE);
            }
        });

    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(0);
        if (DetectConnection.checkInternetConnection(getActivity())) {

            getCloserList();

            //Enquiry Cunt
            getEnquiryBookCount();
            getEnquiryOpenCount();
            getEnquiryLostCount();
            getEnquiryCloseCount();

            //Today Enquiry
            getTodayEnquiry();

            //Activity Count
            getActivityOpenCount();
            getActivityTotalCount();
            getActivityCloseCount();

        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getActivityOpenCount() {

        Call<List<CountResponse>> call = Api.getClient().getActivityOpenCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(8).setText("" + response.body().get(0).getPendingEnquiryFollowups());
                } else {
                    textViews.get(8).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryWonCountError", "" + t.getMessage());
                textViews.get(8).setText("0");
            }
        });

    }

    private void getActivityTotalCount() {

        Call<List<CountResponse>> call = Api.getClient().getActivityTotalCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(9).setText("" + response.body().get(0).getClosedEnquiryFollowups());
                } else {
                    textViews.get(9).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryWonCountError", "" + t.getMessage());
                textViews.get(9).setText("0");
            }
        });

    }

    private void getActivityCloseCount() {

        Call<List<CountResponse>> call = Api.getClient().getActivityCloseCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(10).setText("" + response.body().get(0).getTotalEnquiryFollowups());
                } else {
                    textViews.get(10).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryWonCountError", "" + t.getMessage());
                textViews.get(10).setText("0");
            }
        });

    }

    private void getTodayEnquiryFollowup() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<List<TodayFollowupResponse>> call = Api.getClient().getTodayFollowup("" + MainPage.userId);
        call.enqueue(new Callback<List<TodayFollowupResponse>>() {
            @Override
            public void onResponse(Call<List<TodayFollowupResponse>> call, Response<List<TodayFollowupResponse>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    todayFollowupResponseList = response.body();
                    if (todayFollowupResponseList.size() > 0) {

                        TodayFollowUpAdapter followUpAdapter = new TodayFollowUpAdapter(getActivity(), todayFollowupResponseList, "enquiry");
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(followUpAdapter);
                        followUpAdapter.notifyDataSetChanged();
                        followUpAdapter.notifyItemInserted(todayFollowupResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        recyclerView.setVisibility(View.VISIBLE);
                        textView.get(2).setVisibility(View.GONE);

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textView.get(2).setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textView.get(2).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TodayFollowupResponse>> call, Throwable t) {
                Log.e("todayFollowupError", "" + t.getMessage());
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textView.get(2).setVisibility(View.VISIBLE);
            }
        });

    }

    private void getTodayEnquiry() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<List<EnquiryResponse>> call = Api.getClient().getTodayEnquiry("" + MainPage.userId);
        call.enqueue(new Callback<List<EnquiryResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryResponse>> call, Response<List<EnquiryResponse>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    todayEnquiryResponseList = response.body();
                    if (todayEnquiryResponseList.size() > 0) {

                        EnquiryAdapter adapter = new EnquiryAdapter(getActivity(), todayEnquiryResponseList, categoryResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        adapter.notifyDataSetChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                        textView.get(2).setVisibility(View.GONE);

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textView.get(2).setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textView.get(2).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<EnquiryResponse>> call, Throwable t) {
                Log.e("todayEnquiryError", "" + t.getMessage());
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textView.get(2).setVisibility(View.VISIBLE);
            }
        });

    }

    private void getEnquiryCloseCount() {

        Call<List<CountResponse>> call = Api.getClient().getEnquiryCloseCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(7).setText("" + response.body().get(0).getCloseEnquiry());
                } else {
                    textViews.get(7).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryWonCountError", "" + t.getMessage());
                textViews.get(0).setText("0");
            }
        });

    }

    private void getEnquiryOpenCount() {

        Call<List<CountResponse>> call = Api.getClient().getEnquiryOpenCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(6).setText("" + response.body().get(0).getOpenEnquiry());
                } else {
                    textViews.get(6).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryWonCountError", "" + t.getMessage());
                textViews.get(0).setText("0");
            }
        });

    }

    private void getEnquiryLostCount() {

        Call<List<CountResponse>> call = Api.getClient().getEnquiryLostCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(1).setText("" + response.body().get(0).getLostEnquiry());
                } else {
                    textViews.get(1).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryLostCountError", "" + t.getMessage());
                textViews.get(1).setText("0");
            }
        });

    }

    private void getEnquiryBookCount() {

        Call<List<CountResponse>> call = Api.getClient().getEnquiryBookCount("" + MainPage.userId);
        call.enqueue(new Callback<List<CountResponse>>() {
            @Override
            public void onResponse(Call<List<CountResponse>> call, Response<List<CountResponse>> response) {

                if (response.isSuccessful()) {
                    textViews.get(0).setText("" + response.body().get(0).getWonEnquiry());
                } else {
                    textViews.get(0).setText("0");
                }

            }

            @Override
            public void onFailure(Call<List<CountResponse>> call, Throwable t) {
                Log.e("enquiryWonCountError", "" + t.getMessage());
                textViews.get(0).setText("0");
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