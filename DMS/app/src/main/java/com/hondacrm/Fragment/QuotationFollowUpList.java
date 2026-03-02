package com.hondacrm.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.google.android.material.snackbar.Snackbar;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Adapter.FollowUpAdapter;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.FollowupResponse;
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


public class QuotationFollowUpList extends Fragment {

    public String quotationId;
    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    int page = 1, maxPage;
    List<FollowupResponse> followupResponseList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quotation_follow_up_list, container, false);
        ButterKnife.bind(this, view);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            Bundle bundle = getArguments();
            quotationId = bundle.getString("quotationId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new QuotationList(), true);
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
                        ((MainPage) getActivity()).loadFragment(new QuotationList(), true);
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
                    getFollowUpList();
                }
            }
        });

        return view;

    }

    @OnClick({R.id.addQuotationFollowUp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addQuotationFollowUp:

                AddQuotationFollowUp addQuotationFollowUp = new AddQuotationFollowUp();
                Bundle bundle = new Bundle();
                bundle.putString("quotationId", "" + quotationId);
                addQuotationFollowUp.setArguments(bundle);
                ((MainPage) getActivity()).loadFragment(addQuotationFollowUp, true);

                break;
        }
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getFollowUpList();
        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).show();
        }
    }

    private void getFollowUpList() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<List<FollowupResponse>> call = Api.getClient().getQuotationFollowUpPaginationList(quotationId, page, 10);
        call.enqueue(new Callback<List<FollowupResponse>>() {
            @Override
            public void onResponse(Call<List<FollowupResponse>> call, Response<List<FollowupResponse>> response) {

                if (response.isSuccessful()) {

                    followupResponseList.addAll(response.body());
                    if (followupResponseList.size() > 0) {

                        // get headers
                        String headers = response.headers().get("Paging-Headers");
                        try {
                            JSONObject jsonObject = new JSONObject(headers);
                            maxPage = Integer.parseInt(jsonObject.getString("totalPages"));
                            Log.e("totalPages", "" + maxPage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        FollowUpAdapter adapter = new FollowUpAdapter(getActivity(), followupResponseList, "quotation");
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(followupResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<FollowupResponse>> call, Throwable t) {
                Log.e("followUpError", "" + t.getMessage());
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
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