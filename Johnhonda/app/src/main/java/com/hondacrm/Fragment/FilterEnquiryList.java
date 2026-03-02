package com.hondacrm.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.hondacrm.Activity.MainPage;
import com.hondacrm.Adapter.EnquiryAdapter;
import com.hondacrm.Adapter.QuotationAdapter;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.EnquiryResponse;
import com.hondacrm.Module.QuotationResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilterEnquiryList extends Fragment {

    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noDataFound)
    TextView noDataFound;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    int page = 1, maxPage;
    List<QuotationResponse> quotationResponseList = new ArrayList<>();
    List<EnquiryResponse> enquiryResponseList = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();

    String mobileNumber, categoryId, modelNameId, variantId, colorId, statusId, fromDate, toDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_filter_enquiry_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        Bundle bundle = getArguments();
        if (bundle != null) {
            mobileNumber = bundle.getString("mobileNumber");
            categoryId = bundle.getString("categoryId");
            modelNameId = bundle.getString("modelNameId");
            variantId = bundle.getString("variantId");
            colorId = bundle.getString("colorId");
            statusId = bundle.getString("statusId");
            fromDate = bundle.getString("fromDate");
            toDate = bundle.getString("toDate");
        }

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //check condition
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                //Increase page size
                page++;
                if (page <= maxPage) {
                    getFilterEnquiryList();
                }
            }
        });


        return view;
    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getCloserList();
            getFilterEnquiryList();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getFilterEnquiryList() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noDataFound.setVisibility(View.GONE);

        enquiryResponseList.clear();
        recyclerView.clearOnScrollListeners();

        if (!mobileNumber.equalsIgnoreCase("")) {
            mobileNumber = "'" + mobileNumber + "'";
        } else {
            mobileNumber = "";
        }

        Call<List<EnquiryResponse>> call = Api.getClient().getFilterEnquiryList(mobileNumber, categoryId, modelNameId, variantId, statusId, fromDate, toDate, MainPage.userId, page, 10);
        call.enqueue(new Callback<List<EnquiryResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryResponse>> call, Response<List<EnquiryResponse>> response) {

                if (response.isSuccessful()) {

                    enquiryResponseList = response.body();
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
                        recyclerView.setVisibility(View.VISIBLE);
                        noDataFound.setVisibility(View.GONE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<EnquiryResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
                Log.e("quotationError", "" + t.getMessage());
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.error_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextView txtYes = dialog.findViewById(R.id.yes);

                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    private void getFilterQuotationList() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noDataFound.setVisibility(View.GONE);

        quotationResponseList.clear();
        recyclerView.clearOnScrollListeners();

        Call<List<QuotationResponse>> call = Api.getClient().getFilterQuotationList(mobileNumber, categoryId, modelNameId, variantId, fromDate, toDate, MainPage.userId);
        call.enqueue(new Callback<List<QuotationResponse>>() {
            @Override
            public void onResponse(Call<List<QuotationResponse>> call, Response<List<QuotationResponse>> response) {

                if (response.isSuccessful()) {

                    quotationResponseList = response.body();
                    if (quotationResponseList.size() > 0) {

                        QuotationAdapter adapter = new QuotationAdapter(getActivity(), quotationResponseList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(quotationResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        noDataFound.setVisibility(View.GONE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<QuotationResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
                Log.e("quotationError", "" + t.getMessage());
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.error_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextView txtYes = dialog.findViewById(R.id.yes);

                txtYes.setOnClickListener(new View.OnClickListener() {
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